package com.meti.node.value.compound.variable;

import com.meti.node.Node;
import com.meti.node.Type;

public class FieldNodeBuilder {
	private Type childType = null;
	private Node instanceArray = null;
	private String name = null;
	private int order = -1;

	public Node build() {
		return new FieldNode(instanceArray, order, childType, name);
	}

	public FieldNodeBuilder withInstanceArray(Node instanceArray) {
		this.instanceArray = instanceArray;
		return this;
	}

	public FieldNodeBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public FieldNodeBuilder withOrder(int order) {
		this.order = order;
		return this;
	}

	public FieldNodeBuilder withType(Type childType) {
		this.childType = childType;
		return this;
	}
}