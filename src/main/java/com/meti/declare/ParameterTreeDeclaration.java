package com.meti.declare;

import com.meti.node.Type;

public class ParameterTreeDeclaration extends TreeDeclaration {
    ParameterTreeDeclaration(String name, Type type, Declaration parent) {
        super(name, parent, type);
    }

    @Override
    public boolean isParameter() {
        return true;
    }
}
