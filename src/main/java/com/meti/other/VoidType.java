package com.meti.other;

import com.meti.Type;

public class VoidType implements Type {
    @Override
    public boolean isNamed() {
        return false;
    }

    @Override
    public String render() {
        return "void";
    }
}
