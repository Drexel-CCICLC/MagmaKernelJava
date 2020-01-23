package com.meti.node.value.compound.variable;

import com.meti.declare.Declaration;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.bracket.struct.StructType;

import java.util.LinkedList;

public class FieldNode implements Node {
	private final Type childType;
	private final String field;
	private final Node instance;
	private final Declaration parent;

	FieldNode(Declaration parent, Node instance, Type childType, String field) {
		this.parent = parent;
		this.instance = instance;
		this.childType = childType;
		this.field = field;
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
		return childType instanceof StructType ?
				parent.toInstance().render() + field :
				instance.render() + "." + field;
	}
}
