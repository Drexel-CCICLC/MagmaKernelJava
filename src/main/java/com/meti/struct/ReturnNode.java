package com.meti.struct;

import com.meti.Node;

import java.util.LinkedList;

public class ReturnNode implements Node {
    private final Node value;

    public ReturnNode(Node value) {
        this.value = value;
    }

	@Override
	public LinkedList<Node> children() {
		return new LinkedList<>();
	}

	@Override
    public boolean isParent() {
        return false;
    }

    @Override
    public String render() {
        return "return " + value.render() + ";";
    }
}
