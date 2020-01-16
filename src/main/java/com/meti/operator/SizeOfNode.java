package com.meti.operator;

import com.meti.Node;
import com.meti.Type;

import java.util.LinkedList;

public class SizeOfNode implements Node {
    private final Type type;

    public SizeOfNode(Type type) {
        this.type = type;
    }

	@Override
	public LinkedList<Node> children() {
		return new LinkedList<>();
	}

	@Override
    public String render() {
        return "sizeof(" + type.render() + ")";
    }

    @Override
    public boolean isParent() {
		return false;
    }
}
