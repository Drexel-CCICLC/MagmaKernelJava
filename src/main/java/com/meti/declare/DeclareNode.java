package com.meti.declare;

import com.meti.Node;
import com.meti.Type;

class DeclareNode implements Node {
    private final Type type;
    private final String name;
    private final Node value;

    DeclareNode(Type type, String name, Node value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    @Override
    public String render() {
        String before = (type.isNamed()) ? type.render() : type.render() + " " + name;
        String after = value.render();
        return before + "=" + after;
    }

    @Override
    public boolean isParent() {
		return false;
    }
}
