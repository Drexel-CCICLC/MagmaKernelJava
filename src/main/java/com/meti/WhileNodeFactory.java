package com.meti;

import java.util.List;
import java.util.Optional;

public class WhileNodeFactory implements NodeFactory {
	@Override
	public Optional<Node> parse(String value, Parser parser) {
		if (value.startsWith("while(")) {
			int index = -1;
			int depth = 0;
			char[] charArray = value.substring(6).toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				char c = charArray[i];
				if (c == '(') {
					depth++;
				} else if (c == ')') {
					if (depth == 0) {
						index = i;
						break;
					} else {
						depth--;
					}
				}
			}
			var condition = parser.parse(value.substring(6, index + 6));
			var content = parser.parse(value.substring(index + 7));
			return Optional.of(new WhileNode(condition, content));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Struct> parse(String value) {
		return Optional.empty();
	}

	private static final class WhileNode extends AbstractParentNode {
		public WhileNode(Node condition, Node block) {
			this(null, List.of(condition, block));
		}

		public WhileNode(Struct struct, List<Node> children) {
			super(struct, children);
		}

		@Override
		public String compile(Aliaser aliaser) {
			var condition = children.get(0).compile(aliaser);
			var block = children.get(1).compile(aliaser);
			return "while(" + condition + ")" + block;
		}

		@Override
		public Node transform() {
			return this;
		}
	}
}
