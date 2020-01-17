package com.meti.node.bracket.declare;

import com.meti.node.Node;
import com.meti.node.Type;

import java.util.LinkedList;

public class DeclareNode implements Node {
	private final String name;
	private final Type type;
	private final Node value;

	public DeclareNode(Type type, String name, Node value) {
		this.type = type;
		this.name = name;
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
		String before = type.renderWithName(name);
		String after = value.render();
        return after.isBlank() ? "" : before + "=" + after + ";";
	}
}
