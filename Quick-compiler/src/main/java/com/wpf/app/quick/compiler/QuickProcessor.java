package com.wpf.app.quick.compiler;

import com.wpf.app.quick.annotations.BindData2View;
import com.wpf.app.quick.annotations.BindView;
import com.wpf.app.quick.annotations.GroupView;
import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeScanner;
import com.wpf.app.quick.annotations.SaveId;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.tools.Diagnostic.Kind;

import net.ltgt.gradle.incap.IncrementalAnnotationProcessor;
import net.ltgt.gradle.incap.IncrementalAnnotationProcessorType;

import static com.wpf.app.quick.annotations.internal.Constants.NO_RES_ID;
import static java.util.Objects.requireNonNull;
import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.ElementKind.INTERFACE;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.STATIC;

@AutoService(Processor.class)
@IncrementalAnnotationProcessor(IncrementalAnnotationProcessorType.DYNAMIC)
public final class QuickProcessor extends AbstractProcessor {
    private static final String OPTION_SDK_INT = "com.wpf.app.quick.compiler.minSdk";
    private static final String OPTION_DEBUGGABLE = "com.wpf.app.quick.compiler.debuggable";
    static final Id NO_ID = new Id(NO_RES_ID);
    static final String VIEW_TYPE = "android.view.View";
    static final String ACTIVITY_TYPE = "android.app.Activity";
    static final String DIALOG_TYPE = "android.app.Dialog";
    private static final String NULLABLE_ANNOTATION_NAME = "Nullable";

    private Filer filer;
    private @Nullable
    Trees trees;

    private int sdk = 1;
    private boolean debuggable = false;

    private final RScanner rScanner = new RScanner();

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        String sdk = env.getOptions().get(OPTION_SDK_INT);
        if (sdk != null) {
            try {
                this.sdk = Integer.parseInt(sdk);
            } catch (NumberFormatException e) {
                env.getMessager()
                        .printMessage(Kind.WARNING, "Unable to parse supplied minSdk option '"
                                + sdk
                                + "'. Falling back to API 1 support.");
            }
        }

        String debugStr = env.getOptions().get(OPTION_DEBUGGABLE);
        if (debugStr != null && !debugStr.isEmpty()) {
            debuggable = !"false".equals(debugStr);
        }
        filer = env.getFiler();
        try {
            trees = Trees.instance(processingEnv);
        } catch (IllegalArgumentException ignored) {
            try {
                // Get original ProcessingEnvironment from Gradle-wrapped one or KAPT-wrapped one.
                for (Field field : processingEnv.getClass().getDeclaredFields()) {
                    if (field.getName().equals("delegate") || field.getName().equals("processingEnv")) {
                        field.setAccessible(true);
                        ProcessingEnvironment javacEnv = (ProcessingEnvironment) field.get(processingEnv);
                        trees = Trees.instance(javacEnv);
                        break;
                    }
                }
            } catch (Throwable ignored2) {
            }
        }
    }

    @Override
    public Set<String> getSupportedOptions() {
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        builder.add(OPTION_SDK_INT, OPTION_DEBUGGABLE);
        if (trees != null) {
            builder.add(IncrementalAnnotationProcessorType.ISOLATING.getProcessorOption());
        }
        return builder.build();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();

        annotations.add(BindView.class);
        annotations.add(GroupView.class);
        annotations.add(BindData2View.class);
        annotations.add(SaveId.class);

        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> elements, RoundEnvironment env) {
        Map<TypeElement, BindingSet> bindingMap = findAndParseTargets(env);

        for (Map.Entry<TypeElement, BindingSet> entry : bindingMap.entrySet()) {
            TypeElement typeElement = entry.getKey();
            BindingSet binding = entry.getValue();

            JavaFile javaFile = binding.brewJava(sdk, debuggable);
            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                error(typeElement, "Unable to write binding for type %s: %s", typeElement, e.getMessage());
            }
        }

        return false;
    }

    private Map<TypeElement, BindingSet> findAndParseTargets(RoundEnvironment env) {
        Map<TypeElement, BindingSet.Builder> builderMap = new LinkedHashMap<>();
        Set<TypeElement> erasedTargetNames = new LinkedHashSet<>();

        // Process each @BindData2View element.
        for (Element element : env.getElementsAnnotatedWith(SaveId.class)) {
            // we don't SuperficialValidation.validateElement(element)
            // so that an unresolved View type can be generated by later processing rounds
            try {
                parseSaveId(element, builderMap, erasedTargetNames);
            } catch (Exception e) {
                logParsingError(element, SaveId.class, e);
            }
        }

        // Process each @BindData2View element.
        for (Element element : env.getElementsAnnotatedWith(BindData2View.class)) {
            // we don't SuperficialValidation.validateElement(element)
            // so that an unresolved View type can be generated by later processing rounds
            try {
                parseSaveId(element, builderMap, erasedTargetNames);
            } catch (Exception e) {
                logParsingError(element, BindData2View.class, e);
            }
        }

        // Process each @BindView element.
        for (Element element : env.getElementsAnnotatedWith(BindView.class)) {
            // we don't SuperficialValidation.validateElement(element)
            // so that an unresolved View type can be generated by later processing rounds
            try {
                parseBindView(element, builderMap, erasedTargetNames);
            } catch (Exception e) {
                logParsingError(element, BindView.class, e);
            }
        }

        // Process each @GroupView element.
        for (Element element : env.getElementsAnnotatedWith(GroupView.class)) {
            // we don't SuperficialValidation.validateElement(element)
            // so that an unresolved View type can be generated by later processing rounds
            try {
                parseGroupView(element, builderMap, erasedTargetNames);
            } catch (Exception e) {
                logParsingError(element, GroupView.class, e);
            }
        }

        Map<TypeElement, ClasspathBindingSet> classpathBindings =
                findAllSupertypeBindings(builderMap, erasedTargetNames);

        // Associate superclass binders with their subclass binders. This is a queue-based tree walk
        // which starts at the roots (superclasses) and walks to the leafs (subclasses).
        Deque<Map.Entry<TypeElement, BindingSet.Builder>> entries =
                new ArrayDeque<>(builderMap.entrySet());
        Map<TypeElement, BindingSet> bindingMap = new LinkedHashMap<>();
        while (!entries.isEmpty()) {
            Map.Entry<TypeElement, BindingSet.Builder> entry = entries.removeFirst();

            TypeElement type = entry.getKey();
            BindingSet.Builder builder = entry.getValue();

            TypeElement parentType = findParentType(type, erasedTargetNames, classpathBindings.keySet());
            if (parentType == null) {
                bindingMap.put(type, builder.build());
            } else {
                BindingInformationProvider parentBinding = bindingMap.get(parentType);
                if (parentBinding == null) {
                    parentBinding = classpathBindings.get(parentType);
                }
                if (parentBinding != null) {
                    builder.setParent(parentBinding);
                    bindingMap.put(type, builder.build());
                } else {
                    // Has a superclass binding but we haven't built it yet. Re-enqueue for later.
                    entries.addLast(entry);
                }
            }
        }

        return bindingMap;
    }

    private void logParsingError(Element element, Class<? extends Annotation> annotation,
                                 Exception e) {
        StringWriter stackTrace = new StringWriter();
        e.printStackTrace(new PrintWriter(stackTrace));
        error(element, "Unable to parse @%s binding.\n\n%s", annotation.getSimpleName(), stackTrace);
    }

    private boolean isInaccessibleViaGeneratedCode(Class<? extends Annotation> annotationClass,
                                                   String targetThing, Element element) {
        boolean hasError = false;
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();

        // Verify field or method modifiers.
        Set<Modifier> modifiers = element.getModifiers();
        if (modifiers.contains(PRIVATE) || modifiers.contains(STATIC)) {
            error(element, "@%s %s must not be private or static. (%s.%s)",
                    annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(),
                    element.getSimpleName());
            hasError = true;
        }

        // Verify containing type.
        if (enclosingElement.getKind() != CLASS) {
            error(enclosingElement, "@%s %s may only be contained in classes. (%s.%s)",
                    annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(),
                    element.getSimpleName());
            hasError = true;
        }

        // Verify containing class visibility is not private.
        if (enclosingElement.getModifiers().contains(PRIVATE)) {
            error(enclosingElement, "@%s %s may not be contained in private classes. (%s.%s)",
                    annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(),
                    element.getSimpleName());
            hasError = true;
        }

        return hasError;
    }

    private boolean isBindingInWrongPackage(Class<? extends Annotation> annotationClass,
                                            Element element) {
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        String qualifiedName = enclosingElement.getQualifiedName().toString();

        if (qualifiedName.startsWith("android.")) {
            error(element, "@%s-annotated class incorrectly in Android framework package. (%s)",
                    annotationClass.getSimpleName(), qualifiedName);
            return true;
        }
        if (qualifiedName.startsWith("androidx.")) {
            error(element, "@%s-annotated class incorrectly in Android framework package. (%s)",
                    annotationClass.getSimpleName(), qualifiedName);
            return true;
        }
        if (qualifiedName.startsWith("java.")) {
            error(element, "@%s-annotated class incorrectly in Java framework package. (%s)",
                    annotationClass.getSimpleName(), qualifiedName);
            return true;
        }
//        if (qualifiedName.startsWith("javax.")) {
//            error(element, "@%s-annotated class incorrectly in Java framework package. (%s)",
//                    annotationClass.getSimpleName(), qualifiedName);
//            return true;
//        }

        return false;
    }

    private void parseSaveId(Element element, Map<TypeElement, BindingSet.Builder> builderMap,
                             Set<TypeElement> erasedTargetNames) {
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();

        TypeMirror elementType = element.asType();
        if (elementType.getKind() == TypeKind.TYPEVAR) {
            TypeVariable typeVariable = (TypeVariable) elementType;
            elementType = typeVariable.getUpperBound();
        }
        Name simpleName = element.getSimpleName();

        // Assemble information on the field.
        int id = element.getAnnotation(BindData2View.class).id();
        BindingSet.Builder builder = builderMap.get(enclosingElement);
        Id resourceId = elementToId(element, BindData2View.class, id);
        if (builder != null) {
//            String existingBindingName = builder.findExistingBindingName(resourceId);
//            if (existingBindingName != null) {
//                error(element, "Attempt to use @%s for an already bound ID %d on '%s'. (%s.%s)",
//                        BindData2View.class.getSimpleName(), id, existingBindingName,
//                        enclosingElement.getQualifiedName(), element.getSimpleName());
//                return;
//            }
        } else {
            builder = getOrCreateBindingBuilder(builderMap, enclosingElement);
        }

        String name = simpleName.toString();
        TypeName type = TypeName.get(elementType);
        boolean required = isFieldRequired(element);

        builder.addField(resourceId, name + BindData2View.class.getSimpleName(), new FieldViewBinding(name, type, required, false, true, false));

        // Add the type-erased version to the valid binding targets set.
        erasedTargetNames.add(enclosingElement);
    }

    private void parseGroupView(Element element, Map<TypeElement, BindingSet.Builder> builderMap,
                                Set<TypeElement> erasedTargetNames) {
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();

        TypeMirror elementType = element.asType();
        if (elementType.getKind() == TypeKind.TYPEVAR) {
            TypeVariable typeVariable = (TypeVariable) elementType;
            elementType = typeVariable.getUpperBound();
        }
        Name simpleName = element.getSimpleName();

        // Assemble information on the field.
        int[] idList = element.getAnnotation(GroupView.class).idList();
        BindingSet.Builder builder = builderMap.get(enclosingElement);
        String name = simpleName.toString();
        TypeName type = TypeName.get(elementType);
        boolean required = isFieldRequired(element);
        boolean groupView = hasAnnotationWithName(element, GroupView.class.getSimpleName());
        Id[] resourceIdList = new Id[idList.length];
        for (int i = 0; i < idList.length; ++i) {
            resourceIdList[i] = elementToId(element, GroupView.class, idList[i]);
        }
        if (builder != null) {
            String existingBindingName = builder.findExistingBindingName(resourceIdList);
            if (existingBindingName != null) {
                error(element, "Attempt to use @%s for an already bound ID %d on '%s'. (%s.%s)",
                        GroupView.class.getSimpleName(), idList, existingBindingName,
                        enclosingElement.getQualifiedName(), element.getSimpleName());
                return;
            }
        } else {
            builder = getOrCreateBindingBuilder(builderMap, enclosingElement);
        }
        builder.addField(resourceIdList, new FieldViewBinding(name, type, required, groupView, false, false));

        // Add the type-erased version to the valid binding targets set.
        erasedTargetNames.add(enclosingElement);
    }

    private void parseBindView(Element element, Map<TypeElement, BindingSet.Builder> builderMap,
                               Set<TypeElement> erasedTargetNames) {
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();

        // Start by verifying common generated code restrictions.
//        boolean hasError = isInaccessibleViaGeneratedCode(BindView.class, "fields", element)
//                || isBindingInWrongPackage(BindView.class, element);

        // Verify that the target type extends from View.
        TypeMirror elementType = element.asType();
        if (elementType.getKind() == TypeKind.TYPEVAR) {
            TypeVariable typeVariable = (TypeVariable) elementType;
            elementType = typeVariable.getUpperBound();
        }
        Name qualifiedName = enclosingElement.getQualifiedName();
        Name simpleName = element.getSimpleName();
        if (!isSubtypeOfType(elementType, VIEW_TYPE) && !isInterface(elementType)) {
            if (elementType.getKind() == TypeKind.ERROR) {
                note(element, "@%s field with unresolved type (%s) "
                                + "must elsewhere be generated as a View or interface. (%s.%s)",
                        BindView.class.getSimpleName(), elementType, qualifiedName, simpleName);
            } else {
                error(element, "@%s fields must extend from View or be an interface. (%s.%s)",
                        BindView.class.getSimpleName(), qualifiedName, simpleName);
//                hasError = true;
            }
        }

//        if (hasError) {
//            return;
//        }

        // Assemble information on the field.
        int id = element.getAnnotation(BindView.class).value();
        BindingSet.Builder builder = builderMap.get(enclosingElement);
        Id resourceId = elementToId(element, BindView.class, id);
        if (builder != null) {
            String existingBindingName = builder.findExistingBindingName(resourceId);
            if (existingBindingName != null) {
                error(element, "Attempt to use @%s for an already bound ID %d on '%s'. (%s.%s)",
                        BindView.class.getSimpleName(), id, existingBindingName,
                        enclosingElement.getQualifiedName(), element.getSimpleName());
                return;
            }
        } else {
            builder = getOrCreateBindingBuilder(builderMap, enclosingElement);
        }

        String name = simpleName.toString();
        TypeName type = TypeName.get(elementType);
        boolean required = isFieldRequired(element);
        boolean groupView = hasAnnotationWithName(element, GroupView.class.getSimpleName());

        builder.addField(resourceId, BindView.class.getSimpleName(), new FieldViewBinding(name, type, required, groupView));

        // Add the type-erased version to the valid binding targets set.
        erasedTargetNames.add(enclosingElement);
    }

    private boolean isInterface(TypeMirror typeMirror) {
        return typeMirror instanceof DeclaredType
                && ((DeclaredType) typeMirror).asElement().getKind() == INTERFACE;
    }

    static boolean isSubtypeOfType(TypeMirror typeMirror, String otherType) {
        if (isTypeEqual(typeMirror, otherType)) {
            return true;
        }
        if (typeMirror.getKind() != TypeKind.DECLARED) {
            return false;
        }
        DeclaredType declaredType = (DeclaredType) typeMirror;
        List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
        if (typeArguments.size() > 0) {
            StringBuilder typeString = new StringBuilder(declaredType.asElement().toString());
            typeString.append('<');
            for (int i = 0; i < typeArguments.size(); i++) {
                if (i > 0) {
                    typeString.append(',');
                }
                typeString.append('?');
            }
            typeString.append('>');
            if (typeString.toString().equals(otherType)) {
                return true;
            }
        }
        Element element = declaredType.asElement();
        if (!(element instanceof TypeElement)) {
            return false;
        }
        TypeElement typeElement = (TypeElement) element;
        TypeMirror superType = typeElement.getSuperclass();
        if (isSubtypeOfType(superType, otherType)) {
            return true;
        }
        for (TypeMirror interfaceType : typeElement.getInterfaces()) {
            if (isSubtypeOfType(interfaceType, otherType)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isTypeEqual(TypeMirror typeMirror, String otherType) {
        return otherType.equals(typeMirror.toString());
    }

    private BindingSet.Builder getOrCreateBindingBuilder(
            Map<TypeElement, BindingSet.Builder> builderMap, TypeElement enclosingElement) {
        BindingSet.Builder builder = builderMap.get(enclosingElement);
        if (builder == null) {
            builder = BindingSet.newBuilder(enclosingElement);
            builderMap.put(enclosingElement, builder);
        }
        return builder;
    }

    /**
     * Finds the parent binder type in the supplied sets, if any.
     */
    private @Nullable
    TypeElement findParentType(
            TypeElement typeElement, Set<TypeElement> parents, Set<TypeElement> classpathParents) {
        while (true) {
            typeElement = getSuperClass(typeElement);
            if (typeElement == null || parents.contains(typeElement)
                    || classpathParents.contains(typeElement)) {
                return typeElement;
            }
        }
    }

    private Map<TypeElement, ClasspathBindingSet> findAllSupertypeBindings(
            Map<TypeElement, BindingSet.Builder> builderMap, Set<TypeElement> processedInThisRound) {
        Map<TypeElement, ClasspathBindingSet> classpathBindings = new HashMap<>();

        Set<Class<? extends Annotation>> supportedAnnotations = getSupportedAnnotations();
        Set<Class<? extends Annotation>> requireViewInConstructor =
                ImmutableSet.<Class<? extends Annotation>>builder().add(BindView.class).add(GroupView.class).add(BindData2View.class)
                        .add(SaveId.class).build();
        supportedAnnotations.removeAll(requireViewInConstructor);

        for (TypeElement typeElement : builderMap.keySet()) {
            // Make sure to process superclass before subclass. This is because if there is a class that
            // requires a View in the constructor, all subclasses need it as well.
            Deque<TypeElement> superClasses = new ArrayDeque<>();
            TypeElement superClass = getSuperClass(typeElement);
            while (superClass != null && !processedInThisRound.contains(superClass)
                    && !classpathBindings.containsKey(superClass)) {
                superClasses.addFirst(superClass);
                superClass = getSuperClass(superClass);
            }

            boolean parentHasConstructorWithView = false;
            while (!superClasses.isEmpty()) {
                TypeElement superclass = superClasses.removeFirst();
                ClasspathBindingSet classpathBinding =
                        findBindingInfoForType(superclass, requireViewInConstructor, supportedAnnotations,
                                parentHasConstructorWithView);
                if (classpathBinding != null) {
                    parentHasConstructorWithView |= classpathBinding.constructorNeedsView();
                    classpathBindings.put(superclass, classpathBinding);
                }
            }
        }
        return ImmutableMap.copyOf(classpathBindings);
    }

    private @Nullable
    ClasspathBindingSet findBindingInfoForType(
            TypeElement typeElement, Set<Class<? extends Annotation>> requireConstructorWithView,
            Set<Class<? extends Annotation>> otherAnnotations, boolean needsConstructorWithView) {
        boolean foundSupportedAnnotation = false;
        for (Element enclosedElement : typeElement.getEnclosedElements()) {
            for (Class<? extends Annotation> bindViewAnnotation : requireConstructorWithView) {
                if (enclosedElement.getAnnotation(bindViewAnnotation) != null) {
                    return new ClasspathBindingSet(true, typeElement);
                }
            }
            for (Class<? extends Annotation> supportedAnnotation : otherAnnotations) {
                if (enclosedElement.getAnnotation(supportedAnnotation) != null) {
                    if (needsConstructorWithView) {
                        return new ClasspathBindingSet(true, typeElement);
                    }
                    foundSupportedAnnotation = true;
                }
            }
        }
        if (foundSupportedAnnotation) {
            return new ClasspathBindingSet(false, typeElement);
        } else {
            return null;
        }
    }

    private @Nullable
    TypeElement getSuperClass(TypeElement typeElement) {
        TypeMirror type = typeElement.getSuperclass();
        if (type.getKind() == TypeKind.NONE) {
            return null;
        }
        return (TypeElement) ((DeclaredType) type).asElement();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    private void error(Element element, String message, Object... args) {
        printMessage(Kind.ERROR, element, message, args);
    }

    private void note(Element element, String message, Object... args) {
        printMessage(Kind.NOTE, element, message, args);
    }

    private void printMessage(Kind kind, Element element, String message, Object[] args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }

        processingEnv.getMessager().printMessage(kind, message, element);
    }

    private Id elementToId(Element element, Class<? extends Annotation> annotation, int value) {
        JCTree tree = (JCTree) trees.getTree(element, getMirror(element, annotation));
        if (tree != null) { // tree can be null if the references are compiled types and not source
            rScanner.reset();
            tree.accept(rScanner);
            if (!rScanner.resourceIds.isEmpty()) {
                return rScanner.resourceIds.get(value);
            }
        }
        return new Id(value);
    }

    private static boolean hasAnnotationWithName(Element element, String simpleName) {
        for (AnnotationMirror mirror : element.getAnnotationMirrors()) {
            String annotationName = mirror.getAnnotationType().asElement().getSimpleName().toString();
            if (simpleName.equals(annotationName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isFieldRequired(Element element) {
        return !hasAnnotationWithName(element, NULLABLE_ANNOTATION_NAME);
    }

    private static @Nullable
    AnnotationMirror getMirror(Element element,
                               Class<? extends Annotation> annotation) {
        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            if (annotationMirror.getAnnotationType().toString().equals(annotation.getCanonicalName())) {
                return annotationMirror;
            }
        }
        return null;
    }

    private static class RScanner extends TreeScanner {
        Map<Integer, Id> resourceIds = new LinkedHashMap<>();

        @Override
        public void visitIdent(JCTree.JCIdent jcIdent) {
            super.visitIdent(jcIdent);
            Symbol symbol = jcIdent.sym;
            if (symbol.type instanceof Type.JCPrimitiveType) {
                Id id = parseId(symbol);
                if (id != null) {
                    resourceIds.put(id.value, id);
                }
            }
        }

        @Override
        public void visitSelect(JCTree.JCFieldAccess jcFieldAccess) {
            Symbol symbol = jcFieldAccess.sym;
            Id id = parseId(symbol);
            if (id != null) {
                resourceIds.put(id.value, id);
            }
        }

        @Nullable
        private Id parseId(Symbol symbol) {
            Id id = null;
            if (symbol.getEnclosingElement() != null
                    && symbol.getEnclosingElement().getEnclosingElement() != null
                    && symbol.getEnclosingElement().getEnclosingElement().enclClass() != null) {
                try {
                    int value = (Integer) requireNonNull(((Symbol.VarSymbol) symbol).getConstantValue());
                    id = new Id(value, symbol);
                } catch (Exception ignored) {
                }
            }
            return id;
        }

        @Override
        public void visitLiteral(JCTree.JCLiteral jcLiteral) {
            try {
                int value = (Integer) jcLiteral.value;
                resourceIds.put(value, new Id(value));
            } catch (Exception ignored) {
            }
        }

        void reset() {
            resourceIds.clear();
        }
    }
}
