package com.wpf.app.quick.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

final class FieldViewBinding implements MemberViewBinding {
    private final String name;
    private final TypeName type;
    private final boolean required;
    private final boolean groupView;
    private final boolean isSaveId;
    private final boolean findView;

    FieldViewBinding(String name, TypeName type, boolean required, boolean groupView) {
        this.name = name;
        this.type = type;
        this.required = required;
        this.groupView = groupView;
        this.isSaveId = false;
        this.findView = true;
    }

    FieldViewBinding(String name, TypeName type, boolean required, boolean groupView, boolean isSaveId) {
        this.name = name;
        this.type = type;
        this.required = required;
        this.groupView = groupView;
        this.isSaveId = isSaveId;
        this.findView = true;
    }

    FieldViewBinding(String name, TypeName type, boolean required, boolean groupView, boolean isSaveId, boolean findView) {
        this.name = name;
        this.type = type;
        this.required = required;
        this.groupView = groupView;
        this.isSaveId = isSaveId;
        this.findView = findView;
    }

    public String getName() {
        return name;
    }

    public TypeName getType() {
        return type;
    }

    public ClassName getRawType() {
        if (type instanceof ParameterizedTypeName) {
            return ((ParameterizedTypeName) type).rawType;
        }
        return (ClassName) type;
    }

    @Override
    public String getDescription() {
        return "field '" + name + "'";
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isGroupView() {
        return groupView;
    }

    public boolean isSaveId() {
        return isSaveId;
    }

    public boolean isFindView() {
        return findView;
    }
}
