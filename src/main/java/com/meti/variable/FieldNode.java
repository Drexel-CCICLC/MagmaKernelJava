package com.meti.variable;

import com.meti.Node;
import com.meti.Type;
import com.meti.array.ArrayIndexNode;
import com.meti.cast.CastNode;
import com.meti.integer.IntNode;
import com.meti.point.DereferenceNode;
import com.meti.point.PointerType;

import java.util.LinkedList;

public class FieldNode implements Node {
	private final Type childType;
	private final String name;
	private final int order;
	private final Node parent;

	public FieldNode(Node parent, int order, Type childType, String name) {
		this.parent = parent;
		this.order = order;
		this.childType = childType;
		this.name = name;
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
		if (childType.returnType().isPresent()) {
			return name;
		} else {
			Type pointerType = new PointerType(childType);
			Node orderNode = new IntNode(order);
			Node arrayIndexNode = new ArrayIndexNode(parent, orderNode);
			Node castNode = new CastNode(pointerType, arrayIndexNode);
			Node dereferenceNode = new DereferenceNode(castNode);
			return dereferenceNode.render();
		}
	}
}
