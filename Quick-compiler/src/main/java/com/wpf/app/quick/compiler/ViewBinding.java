package com.wpf.app.quick.compiler;

import com.wpf.app.quick.annotations.internal.ListenerClass;
import com.wpf.app.quick.annotations.internal.ListenerMethod;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

final class ViewBinding {
    private final Id id;
    private final Id[] ids;
    private final Map<ListenerClass, Map<ListenerMethod, Set<MethodViewBinding>>> methodBindings;
    private final @Nullable
    FieldViewBinding fieldBinding;

    ViewBinding(Id id, Map<ListenerClass, Map<ListenerMethod, Set<MethodViewBinding>>> methodBindings,
                @Nullable FieldViewBinding fieldBinding) {
        this.id = id;
        this.ids = null;
        this.methodBindings = methodBindings;
        this.fieldBinding = fieldBinding;
    }

    ViewBinding(Id[] ids, Map<ListenerClass, Map<ListenerMethod, Set<MethodViewBinding>>> methodBindings,
                @Nullable FieldViewBinding fieldBinding) {
        this.id = null;
        this.ids = ids;
        this.methodBindings = methodBindings;
        this.fieldBinding = fieldBinding;
    }

    public Id getId() {
        return id;
    }

    public Id[] getIds() {
        return ids;
    }

    public @Nullable
    FieldViewBinding getFieldBinding() {
        return fieldBinding;
    }

    public Map<ListenerClass, Map<ListenerMethod, Set<MethodViewBinding>>> getMethodBindings() {
        return methodBindings;
    }

    public List<MemberViewBinding> getRequiredBindings() {
        List<MemberViewBinding> requiredBindings = new ArrayList<>();
        if (fieldBinding != null && fieldBinding.isRequired()) {
            requiredBindings.add(fieldBinding);
        }
        for (Map<ListenerMethod, Set<MethodViewBinding>> methodBinding : methodBindings.values()) {
            for (Set<MethodViewBinding> set : methodBinding.values()) {
                for (MethodViewBinding binding : set) {
                    if (binding.isRequired()) {
                        requiredBindings.add(binding);
                    }
                }
            }
        }
        return requiredBindings;
    }

    public boolean isSingleFieldBinding() {
        return methodBindings.isEmpty() && fieldBinding != null;
    }

    public boolean requiresLocal() {
        if (isBoundToRoot()) {
            return false;
        }
        return !isSingleFieldBinding();
    }

    public boolean isBoundToRoot() {
        return QuickProcessor.NO_ID.equals(id);
    }

    public static final class Builder {
        private final Id id;
        private final Id[] ids;

        private final Map<ListenerClass, Map<ListenerMethod, Set<MethodViewBinding>>> methodBindings =
                new LinkedHashMap<>();
        @Nullable
        FieldViewBinding fieldBinding;
        @Nullable
        List<FieldViewBinding> fieldBindings = new ArrayList<>();

        Builder(Id id) {
            this.id = id;
            this.ids = null;
        }

        Builder(Id[] ids) {
            this.id = null;
            this.ids = ids;
        }

        public boolean hasMethodBinding(ListenerClass listener, ListenerMethod method) {
            Map<ListenerMethod, Set<MethodViewBinding>> methods = methodBindings.get(listener);
            return methods != null && methods.containsKey(method);
        }

        public void addMethodBinding(ListenerClass listener, ListenerMethod method,
                                     MethodViewBinding binding) {
            Map<ListenerMethod, Set<MethodViewBinding>> methods = methodBindings.get(listener);
            Set<MethodViewBinding> set = null;
            if (methods == null) {
                methods = new LinkedHashMap<>();
                methodBindings.put(listener, methods);
            } else {
                set = methods.get(method);
            }
            if (set == null) {
                set = new LinkedHashSet<>();
                methods.put(method, set);
            }
            set.add(binding);
        }

        public void setFieldBinding(FieldViewBinding fieldBinding) {
            if (false) {
                fieldBindings.add(fieldBinding);
                return;
            }
            if (this.fieldBinding != null) {
                throw new AssertionError();
            }
            this.fieldBinding = fieldBinding;
        }

        public ViewBinding build() {
            if (ids != null) {
                return new ViewBinding(ids, methodBindings, fieldBinding);
            }
            return new ViewBinding(id, methodBindings, fieldBinding);
        }
    }
}
