package com.meti;

import java.util.Optional;

public class ReturnNodeFactory implements NodeFactory {
	@Override
	public Optional<Node> parse(String value, Parser parser, Node parent) {
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

}
