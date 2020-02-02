package com.meti.node.primitive;

import com.meti.node.Type;

public class AnyType implements Type {

    @Override
    public String render() {
        return "...";
    }

    @Override
    public String render(String name) {
        return render() + " " + name;
    }
}
