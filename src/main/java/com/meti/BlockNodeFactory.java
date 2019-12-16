package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlockNodeFactory implements NodeFactory {
	@Override
	public Optional<Node> parse(String value, Parser parser, Node parent) {
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
					} else {
						current.append(c);
					}
					break;
				case '{':
					depth++;
					current.append(c);
					break;
				case '}':
					depth--;
					current.append(c);
					break;
				default:
					current.append(c);
					break;
			}
		}
		divisions.add(current.toString());
		List<Node> nodes = new ArrayList<>();
		var blockNode = new BlockNode(null, nodes);
		divisions.stream()
				.filter(string -> !string.isBlank())
				.map(string -> parser.parse(string, blockNode))
				.forEach(nodes::add);
		return blockNode;
	}

	@Override
	public Optional<Struct> parse(String value) {
		return Optional.empty();
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
