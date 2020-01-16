package com.meti.integer;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class IntParser implements Parser {
	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
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
