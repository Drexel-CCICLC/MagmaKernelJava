package com.meti.array;

import com.meti.Type;

public class PointerArrayType extends ArrayType {
    public PointerArrayType(Type elementType) {
        super(elementType);
    }

    @Override
    public String render(String name) {
        return render() + " " + name;
    }

    @Override
    public String render() {
        return elementType.render() + "*";
    }
}
