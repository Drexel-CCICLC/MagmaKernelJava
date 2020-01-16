package com.meti.string;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;

import java.util.Optional;

public class StringParser implements Parser {
	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.startsWith("\"") && value.endsWith("\"")) {
			return Optional.of(new StringNode(trim.substring(1, trim.length() - 1)));
		}
		return Optional.empty();
	}
}
