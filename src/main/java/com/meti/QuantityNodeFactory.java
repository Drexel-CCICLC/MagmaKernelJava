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

	private static class QuantityNode extends InheritedNode {
		protected QuantityNode(Node node) {
			super(node);
		}

		@Override
		public String compile(Aliaser aliaser) {
			return "(" + node.compile(new IncrementAliaser()) + ")";
		}

		@Override
		public Node transform() {
			return this;
		}
	}
}
