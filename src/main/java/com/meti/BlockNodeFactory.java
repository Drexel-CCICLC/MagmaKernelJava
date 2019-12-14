package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlockNodeFactory implements NodeFactory {
	@Override
	public Optional<Node> parse(String value, Parser parser) {
		if (value.startsWith("{") && value.endsWith("}")) {
			return Optional.of(build(value.substring(1, value.length() - 1), parser));
		}
		return Optional.empty();
	}

	private Node build(String value, Parser parser) {
		List<String> divisions = new ArrayList<>();
		StringBuilder current = new StringBuilder();
		int depth = 0;
		for (char c : value.toCharArray()) {
			switch (c) {
				case ';':
					if (depth == 0) {
						divisions.add(current.toString());
						current = new StringBuilder();
					}
					break;
				case '{':
					depth++;
					break;
				case '}':
					depth--;
					break;
				default:
					current.append(c);
					break;
			}
		}
		divisions.add(current.toString());
		var nodes = divisions.stream()
				.filter(string -> !string.isEmpty())
				.map(parser::parse)
				.collect(Collectors.toList());
		return new BlockNode(null, nodes);
	}

	private static final class BlockNode extends AbstractParentNode {
		public BlockNode(Struct struct, List<Node> children) {
			super(struct, children);
		}

		@Override
		public String compile(Aliaser aliaser) {
			var values = children.stream()
					.map(node -> node.compile(aliaser))
					.collect(Collectors.joining());
			return "{" + values + "}";
		}

		@Override
		public Node transform() {
			return this;
		}
	}
}
