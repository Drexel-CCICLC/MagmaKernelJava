package com.meti;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Type {
    private final Type child;
    private final String content;
    private final List<Type> parameters;
    private final Type returnType;

    public Type(String content) {
        this(content, null, Collections.emptyList());
    }

    public Type(String content, Type returnType, List<Type> parameters) {
        this(content, returnType, null, parameters);
    }

    public Type(String content, Type returnType, Type child, List<Type> parameters) {
        this.content = content;
        this.returnType = returnType;
        this.child = child;
        this.parameters = parameters;
    }

    public Type(String content, Type child) {
        this(content, null, child);
	}

    public Type(String content, Type returnType, Type child) {
        this(content, returnType, child, Collections.emptyList());
    }

    public Optional<Type> child() {
        return Optional.ofNullable(child);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return Objects.equals(child, type.child) &&
                Objects.equals(content, type.content) &&
                Objects.equals(parameters, type.parameters) &&
                Objects.equals(returnType, type.returnType);
    }

    public List<Type> parameters() {
        return parameters;
    }

    public String render() {
        return content;
    }

    public Optional<Type> returnType() {
        return Optional.ofNullable(returnType);
    }
}
