package com.meti.node.value.compound.variable;

import com.meti.node.Node;
import com.meti.node.Type;

public interface FieldNodeBuilder {
	static FieldNodeBuilder create() {
		return new FieldNodeBuilderImpl();
	}

	Node build();

	FieldNodeBuilder withInstanceArray(Node instanceArray);

	FieldNodeBuilder withName(String name);

	FieldNodeBuilder withOrder(int order);

	FieldNodeBuilder withType(Type childType);

	final class FieldNodeBuilderImpl implements FieldNodeBuilder {
		private final Type childType;
		private final Node instanceArray;
		private final String name;
		private final int order;

		private FieldNodeBuilderImpl() {
			this(null, null, null, -1);
		}

		private FieldNodeBuilderImpl(Type childType, Node instanceArray, String name, int order) {
			this.childType = childType;
			this.instanceArray = instanceArray;
			this.name = name;
			this.order = order;
		}

		@Override
		public Node build() {
			return new FieldNode(instanceArray, order, childType, name);
		}

		@Override
		public FieldNodeBuilder withInstanceArray(Node instanceArray) {
			return new FieldNodeBuilderImpl(childType, instanceArray, name, order);
		}

		@Override
		public FieldNodeBuilder withName(String name) {
			return new FieldNodeBuilderImpl(childType, instanceArray, name, order);
		}

		@Override
		public FieldNodeBuilder withOrder(int order) {
			return new FieldNodeBuilderImpl(childType, instanceArray, name, order);
		}

		@Override
		public FieldNodeBuilder withType(Type childType) {
			return new FieldNodeBuilderImpl(childType, instanceArray, name, order);
		}
	}
}
