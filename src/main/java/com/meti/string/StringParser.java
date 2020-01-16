package com.meti.string;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class StringParser implements Parser {
	private Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.startsWith("\"") && value.endsWith("\"")) {
			return Optional.of(new StringNode(trim.substring(1, trim.length() - 1)));
		}
		return Optional.empty();
	}

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return parse(value, compiler).stream().collect(Collectors.toSet());
	}
}
