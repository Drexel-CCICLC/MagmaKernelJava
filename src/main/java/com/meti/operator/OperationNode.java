package com.meti.operator;

import com.meti.Node;

public class OperationNode implements Node {
    private final Node first;
    private final Node last;
    private final Operation operation;

    public OperationNode(Node first, Node last, Operation operation) {
        this.first = first;
        this.last = last;
        this.operation = operation;
    }

    @Override
    public String render() {
        return first.render() + operation.value() + last.render();
    }
}
