package com.meti.declare;

import com.meti.node.Type;

public class SimpleTreeDeclaration extends TreeDeclaration {
    SimpleTreeDeclaration(String name, Type type, Declaration parent) {
        super(name, parent, type);
    }

    @Override
    public boolean isParameter() {
        return false;
    }

}
