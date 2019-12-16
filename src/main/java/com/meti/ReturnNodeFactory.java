package com.meti;

import java.util.Optional;

public class ReturnNodeFactory implements NodeFactory {
	@Override
	public Optional<Node> parse(String value, Parser parser) {
		var trimmedValue = value.trim();
		if (trimmedValue.startsWith("return ")) {
			var valueString = trimmedValue.substring(7);
			return Optional.of(new ReturnNode(parser.parse(valueString)));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Struct> parse(String value) {
		return Optional.empty();
	}

	private static final class ReturnNode extends AbstractInheritedNode {
		public ReturnNode(Node value) {
			super(value);
		}

		@Override
		public String compile(Aliaser aliaser) {
			return "return " + value.compile(aliaser) + ";";
		}

		@Override
		public Node transform() {
			return this;
		}
	}
}
