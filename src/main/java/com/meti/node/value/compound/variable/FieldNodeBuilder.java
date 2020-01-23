package com.meti.node.value.compound.variable;

import com.meti.declare.Declaration;
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

	FieldNodeBuilder withParent(Declaration parent);

	FieldNodeBuilder withType(Type type);

	final class FieldNodeBuilderImpl implements FieldNodeBuilder {
		private final Type childType;
		private final Node instanceArray;
		private final String name;
		private final Declaration parent;

		private FieldNodeBuilderImpl() {
			this(null, null, null, null);
		}

		private FieldNodeBuilderImpl(Declaration parent, Type childType, Node instanceArray, String name) {
			this.parent = parent;
			this.childType = childType;
			this.instanceArray = instanceArray;
			this.name = name;
		}

		@Override
		public FieldNodeBuilder withParent(Declaration parent) {
			return new FieldNodeBuilderImpl(parent, childType, instanceArray, name);
		}

		@Override
		public Node build() {
			return new FieldNode(parent, instanceArray, childType, name);
		}

		@Override
		public FieldNodeBuilder withInstanceArray(Node instanceArray) {
			return new FieldNodeBuilderImpl(parent, childType, instanceArray, name);
		}

		@Override
		public FieldNodeBuilder withName(String name) {
			return new FieldNodeBuilderImpl(parent, childType, instanceArray, name);
		}

		@Override
		public FieldNodeBuilder withOrder(int order) {
			return new FieldNodeBuilderImpl(parent, childType, instanceArray, name);
		}

		@Override
		public FieldNodeBuilder withType(Type type) {
			return new FieldNodeBuilderImpl(parent, type, instanceArray, name);
		}
	}
}
