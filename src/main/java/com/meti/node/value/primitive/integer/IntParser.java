package com.meti.node.value.primitive.integer;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Parser;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class IntParser implements Parser {
	private Optional<Node> parse(String value, Compiler compiler) {
		try {
			int integer = Integer.parseInt(value.trim());
			return Optional.of(new IntNode(integer));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return parse(value, compiler).stream().collect(Collectors.toSet());
	}
}
