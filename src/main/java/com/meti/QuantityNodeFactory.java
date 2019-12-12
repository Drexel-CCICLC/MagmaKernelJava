package com.meti;

import java.util.Optional;

public class QuantityNodeFactory implements NodeFactory {
	@Override
	public Optional<Node> parse(String value, Parser parser) {
		if (value.startsWith("(") && value.endsWith(")")) {
			Node node = parser.parse(value.substring(1, value.length() - 1));
			return Optional.of(new QuantityNode(node));
		}
		return Optional.empty();
	}

	private static class QuantityNode extends AbstractNode {
		private final Node node;

		protected QuantityNode(Node node) {
			super(node.struct(), node);
			this.node = node;
		}

		@Override
		public String compile() {
			return "(" + node.compile() + ")";
		}

		@Override
		public Node transform() {
			return this;
		}
	}
}
