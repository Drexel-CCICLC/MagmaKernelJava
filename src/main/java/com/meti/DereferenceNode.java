package com.meti;

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
