package com.meti.node.value.compound.operator;

import com.meti.node.Node;

import java.util.LinkedList;

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
	public LinkedList<Node> children() {
		return new LinkedList<>();
	}

	@Override
    public String render() {
        return first.render() + operation.value() + last.render();
    }

    @Override
    public boolean isParent() {
		return false;
    }
}
