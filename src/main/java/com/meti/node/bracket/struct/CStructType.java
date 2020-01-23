package com.meti.node.bracket.struct;

import com.meti.node.NamedType;
import com.meti.node.ObjectType;
import com.meti.node.Node;
import com.meti.node.Type;

import java.util.Optional;

public class CStructType implements NamedType {
    private final String name;

    public CStructType(String name) {
        this.name = name;
    }

    @Override
    public String render() {
        return "struct " + name;
    }

    @Override
    public boolean isNamed() {
        return true;
    }

    @Override
    public String renderWithName(String name) {
        return "struct " + name;
    }
}
