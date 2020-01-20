package com.meti.node.value.compound.variable;

import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.value.compound.cast.CastNode;
import com.meti.node.value.primitive.array.ArrayIndexNode;
import com.meti.node.value.primitive.integer.IntNode;
import com.meti.node.value.primitive.point.DereferenceNode;
import com.meti.node.value.primitive.point.PointerType;

import java.util.LinkedList;

public class FieldNode implements Node {
	private final Type childType;
	private final Node instanceArray;
	private final String name;
	private final int order;

	FieldNode(Node instanceArray, int order, Type childType, String name) {
		this.instanceArray = instanceArray;
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
			Type pointerType = PointerType.pointerOf(childType);
			Node orderNode = new IntNode(order);
			Node arrayIndexNode = new ArrayIndexNode(instanceArray, orderNode);
			Node castNode = new CastNode(pointerType, arrayIndexNode);
			Node dereferenceNode = new DereferenceNode(castNode);
			return dereferenceNode.render();
		}
	}
}
