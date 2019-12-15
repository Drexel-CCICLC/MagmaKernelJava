package com.meti;

import java.util.Optional;
import java.util.Set;

import static com.meti.PrimitiveNodeFactory.PrimitiveStruct.CHAR;

public class OperationNodeFactory implements NodeFactory {
	private final Set<Character> operation = Set.of('+', '-', '*', '/');

	@Override
	public Optional<Node> parse(String value, Parser parser) {
		for (char c : value.toCharArray()) {
			if (operation.contains(c)) {
				int operationCharIndex = value.indexOf(c);
				String s0 = value.substring(0, operationCharIndex);
				String s1 = value.substring(operationCharIndex + 1);
				Node value0 = parser.parse(s0);
				Node value1 = parser.parse(s1);
				return Optional.of(new OperationNode(CHAR, c, value0, value1));
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<Struct> parse(String value) {
		return Optional.empty();
	}

	private static class OperationNode extends AbstractNode {
		private final char value;
		private final Node value0;
		private final Node value1;

		protected OperationNode(Struct struct, char value, Node value0, Node value1) {
			super(struct);
			this.value = value;
			this.value0 = value0;
			this.value1 = value1;
		}

		@Override
		public String compile(Aliaser aliaser) {
			return value0.compile(new IncrementAliaser()) + value + value1.compile(new IncrementAliaser());
		}

		@Override
		public Node transform() {
			return this;
		}
	}
}
