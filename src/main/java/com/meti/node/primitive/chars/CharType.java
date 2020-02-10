package com.meti.node.primitive.chars;

import com.meti.node.Type;
import com.meti.node.ValueType;

public class CharType extends ValueType {
    public static final Type INSTANCE = new CharType();

    @Override
    public String toMagmaString() {
        return "Char";
    }

    @Override
    public String render() {
        return "char";
    }

    @Override
    public String render(String name) {
        return "char " + name;
    }
}
