package com.meti.node.struct;

import com.meti.node.ValueType;
import com.meti.node.declare.Declaration;

public class DefinedObjectType extends ValueType implements ObjectType {
    private final Declaration declaration;

    public DefinedObjectType(Declaration declaration) {
        this.declaration = declaration;
    }

    @Override
    public String render() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String render(String name) {
        return new StructType(declaration.name()).render(name);
    }

    @Override
    public Declaration declaration() {
        return declaration;
    }

    @Override
    public String toMagmaString() {
        return "";
    }
}
