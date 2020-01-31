package com.meti.point;

import com.meti.Node;

public class DereferenceNode implements Node {
    private final Node value;

    public DereferenceNode(Node value) {
        this.value = value;
    }

    @Override
    public String render() {
        return "*" + value.render();
    }
}
