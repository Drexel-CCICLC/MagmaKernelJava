package com.meti.point;

import com.meti.Node;

public class ReferenceNode implements Node {
    private final Node value;

    public ReferenceNode(Node value) {
        this.value = value;
    }

    @Override
    public String render() {
        return "&" + value.render();
    }
}
