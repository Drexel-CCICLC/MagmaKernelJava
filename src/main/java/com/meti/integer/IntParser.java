package com.meti.integer;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;

import java.util.Optional;

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
}
