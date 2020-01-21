package com.meti.node.value.primitive.integer;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Parser;

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
