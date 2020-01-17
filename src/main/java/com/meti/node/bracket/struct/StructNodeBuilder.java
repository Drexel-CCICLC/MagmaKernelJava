package com.meti.node.bracket.struct;

import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.other.VoidType;

import java.util.HashMap;
import java.util.Map;

public interface StructNodeBuilder {
	static StructNodeBuilder create() {
		return new StructNodeBuilderImpl();
	}

	Node create(Generator generator);

	StructNodeBuilder withBlock(Node block);

	StructNodeBuilder withName(String name);

	StructNodeBuilder withParameter(String name, Type type);

	StructNodeBuilder withReturnType(Type returnType);

	final class StructNodeBuilderImpl implements StructNodeBuilder {
		private final Node block;
		private final String name;
		private final Map<String, Type> parameters;
		private final Type returnType;

		private StructNodeBuilderImpl() {
			this(null, null, new HashMap<>(), null);
		}

		private StructNodeBuilderImpl(Node block, String name, Map<String, Type> parameters, Type returnType) {
			this.block = block;
			this.name = name;
			this.parameters = parameters;
			this.returnType = returnType;
		}

		@Override
		public Node create(Generator generator) {
			String name = buildName(generator);
			Type type = buildType();
			return new StructNode(name, parameters, type, block);
		}

		private String buildName(Generator generator) {
			return (null == this.name) ? generator.next() : this.name;
		}

		private Type buildType() {
			return null == this.returnType ? VoidType.INSTANCE() : this.returnType;
		}

		@Override
		public StructNodeBuilder withBlock(Node block) {
			return new StructNodeBuilderImpl(block, name, parameters, returnType);
		}

		@Override
		public StructNodeBuilder withName(String name) {
			return new StructNodeBuilderImpl(block, name, parameters, returnType);
		}

		@Override
		public StructNodeBuilder withParameter(String name, Type type) {
			parameters.put(name, type);
            return this;
        }

		@Override
		public StructNodeBuilder withReturnType(Type returnType) {
			return new StructNodeBuilderImpl(block, name, parameters, returnType);
		}
	}
}
