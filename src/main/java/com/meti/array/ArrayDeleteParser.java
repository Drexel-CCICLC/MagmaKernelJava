package com.meti.array;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;

import java.util.Optional;

public class ArrayDeleteParser implements Parser {
	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.startsWith("delete ")) {
			String valueString = trim.substring(7);
			Node node = compiler.parse(valueString);
			return Optional.of(new ArrayDeleteNode(node));
		}
		return Optional.empty();
	}
}
