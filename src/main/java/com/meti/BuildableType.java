package com.meti;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BuildableType implements Type {
    private final Type child;
    private final String content;
    private final List<Type> parameters;
    private final Type returnType;

    public BuildableType(String content) {
        this(content, null, Collections.emptyList());
    }

    public BuildableType(String content, Type returnType, List<Type> parameters) {
        this(content, returnType, null, parameters);
    }

    public BuildableType(String content, Type returnType, Type child, List<Type> parameters) {
        this.content = content;
        this.returnType = returnType;
        this.child = child;
        this.parameters = parameters;
    }

    public BuildableType(String content, Type child) {
        this(content, null, child);
	}

    public BuildableType(String content, Type returnType, Type child) {
        this(content, returnType, child, Collections.emptyList());
    }

    @Override
    public Optional<Type> child() {
        return Optional.ofNullable(child);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildableType type = (BuildableType) o;
        return Objects.equals(child, type.child) &&
                Objects.equals(content, type.content) &&
                Objects.equals(parameters, type.parameters) &&
                Objects.equals(returnType, type.returnType);
    }

    @Override
    public boolean isPointer() {
        return render().endsWith("*");
    }

    @Override
    public List<Type> parameters() {
        return parameters;
    }

    @Override
    public String render() {
        return content;
    }

    @Override
    public Optional<Type> returnType() {
        return Optional.ofNullable(returnType);
    }
}
