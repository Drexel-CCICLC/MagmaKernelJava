package com.meti;

import java.util.Optional;

public class VariableNodeFactory implements NodeFactory {
	private final NodeTree tree;

	public VariableNodeFactory(NodeTree tree) {
		this.tree = tree;
	}

	@Override
	public Optional<Node> parse(String value, Parser parser) {
		var trimmedValue = value.trim();
		var declaration = tree.locateDeclaration(trimmedValue)
				.orElseThrow(() -> new IllegalStateException(trimmedValue + " is not defined."));
		return Optional.of(new VariableNode(declaration.struct(), trimmedValue));
	}

	@Override
	public Optional<Struct> parse(String value) {
		return Optional.empty();
	}

	private static final class VariableNode extends AbstractNode implements NamedNode {
		private final String name;

		protected VariableNode(Struct struct, String name) {
			super(struct);
			this.name = name;
		}

		@Override
		public String compile(Aliaser aliaser) {
			return aliaser.alias(name);
		}

		@Override
		public Node transform() {
			return this;
		}

		@Override
		public String name() {
			return name;
		}
	}
}
