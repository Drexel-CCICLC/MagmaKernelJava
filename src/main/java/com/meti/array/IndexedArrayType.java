package com.meti.array;

import com.meti.Type;

public class IndexedArrayType extends ArrayType {
    public IndexedArrayType(Type elementType) {
        super(elementType);
    }

    @Override
    public String render(String name) {
        return elementType.render() + " " + name + "[]";
    }

    @Override
    public String render() {
        throw new UnsupportedOperationException();
    }
}
