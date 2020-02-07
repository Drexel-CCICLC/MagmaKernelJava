package com.meti.node.declare;

import com.meti.node.Node;
import com.meti.node.Type;

public class DeclareNode implements Node {
    private final String name;
    private final Type type;
    private final Node value;

    public DeclareNode(Type type, String name, Node value) {
        this.value = value;
        this.type = type;
        this.name = name;
    }

    @Override
    public String render() {
        return null == value ? renderWithoutValue() : renderWithValue(value);
    }

    private String renderWithoutValue() {
        return type.render(name) + ";";
    }

    private String renderWithValue(Node value) {
        String renderedValue = value.render();
        return renderedValue.isBlank() ? "" :
                type.render(name) + "=" + renderedValue + ";";
    }
}
