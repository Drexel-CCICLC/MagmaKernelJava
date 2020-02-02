package com.meti.node.array;

import com.meti.node.Type;

public abstract class ArrayType implements Type {
    protected final Type elementType;

    public ArrayType(Type elementType) {
        this.elementType = elementType;
    }

}
