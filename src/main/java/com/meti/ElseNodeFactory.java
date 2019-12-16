package com.meti;

import java.util.List;
import java.util.Optional;

public class ElseNodeFactory implements NodeFactory {
	@Override
	public Optional<Node> parse(String value, Parser parser, Node parent) {
		if (value.startsWith("else")) {
			return Optional.of(new ElseNode(parser.parse(value.substring(4))));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Struct> parse(String value) {
		return Optional.empty();
	}

	private static final class ElseNode extends AbstractParentNode {
		public ElseNode(Node block) {
			this(null, List.of(block));
		}

		public ElseNode(Struct struct, List<Node> children) {
			super(struct, children);
		}

		@Override
		public String compile(Aliaser aliaser, NodeTree tree) {
			var block = children.get(0).compile(aliaser, tree);
			return "else" + block;
		}

		@Override
		public Node transform() {
			return this;
		}
	}
}
