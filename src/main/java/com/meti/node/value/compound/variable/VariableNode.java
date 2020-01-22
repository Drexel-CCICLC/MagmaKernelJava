package com.meti.node.value.compound.variable;

import com.meti.node.Node;

import java.util.LinkedList;

public class VariableNode implements Node {
    private final String value;
    private final boolean parameter;

    public VariableNode(String value) {
        this(value, false);
    }

    public VariableNode(String value, boolean parameter) {
        this.value = value;
        this.parameter = parameter;
    }

    @Override
    public LinkedList<Node> children() {
        return new LinkedList<>();
    }

    @Override
    public String render() {
        return (parameter) ? "*" + value : value;
    }

    @Override
    public boolean isParent() {
        return false;
    }
}
