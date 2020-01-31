package com.meti.node.transform;

import com.meti.node.Node;

public class OperationNode implements Node {
    private final Node node0;
    private final Node node1;
    private final Operation operation;

    public OperationNode(Node node0, Node node1, Operation operation) {
        this.node0 = node0;
        this.node1 = node1;
        this.operation = operation;
    }

    @Override
    public String render() {
        return node0.render() + operation.getTo() + node1.render();
    }
}
