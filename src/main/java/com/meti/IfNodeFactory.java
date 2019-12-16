package com.meti;

import java.util.List;
import java.util.Optional;

public class IfNodeFactory implements NodeFactory {
	@Override
	public Optional<Node> parse(String value, Parser parser, Node parent) {
		if (value.startsWith("if(")) {
			int index = -1;
			int depth = 0;
			char[] charArray = value.substring(3).toCharArray();
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
			var condition = parser.parse(value.substring(3, index + 3));
			var content = parser.parse(value.substring(index + 4));
			return Optional.of(new IfNode(condition, content));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Struct> parse(String value) {
		return Optional.empty();
	}

	private static final class IfNode extends AbstractParentNode {
		public IfNode(Node condition, Node ifBlock) {
			this(null, List.of(condition, ifBlock));
		}

		public IfNode(Struct struct, List<Node> children) {
			super(struct, children);
		}

		@Override
		public String compile(Aliaser aliaser) {
			var condition = children.get(0).compile(aliaser);
			var ifBlock = children.get(1).compile(aliaser);
			return "if(" + condition + ")" + ifBlock;
		}

		@Override
		public Node transform() {
			return this;
		}
	}
}
