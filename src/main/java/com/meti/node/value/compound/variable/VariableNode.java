package com.meti.node.value.compound.variable;

import com.meti.node.Node;

import java.util.LinkedList;

public class VariableNode implements Node {
    private final String value;

    public VariableNode(String value) {
        this.value = value;
    }

	@Override
	public LinkedList<Node> children() {
		return new LinkedList<>();
	}

	@Override
    public String render() {
        return value;
    }

    @Override
    public boolean isParent() {
		return false;
    }
}
