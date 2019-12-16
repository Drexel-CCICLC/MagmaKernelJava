package com.meti;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArrayNodeFactory implements NodeFactory {

	@Override
	public Optional<Node> parse(String value, Parser parser, Node parent) {
		if (value.startsWith("[") && value.endsWith("]")) {
			String content = value.substring(1, value.length() - 1);
			List<Node> nodes = Arrays.stream(content.split(","))
					.map(String::trim)
					.map(parser::parse)
					.collect(Collectors.toList());
			if (nodes.isEmpty()) throw new IllegalStateException("No content found.");
			Struct arrayMatch = nodes.stream()
					.map(Node::struct)
					.reduce((root, other) -> other.merge(root))
					.orElseThrow();
			return Optional.of(new ArrayNode(new GenericStructImpl("Array", Map.of("T", arrayMatch)), nodes));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Struct> parse(String value) {
		return Optional.empty();
	}

}
