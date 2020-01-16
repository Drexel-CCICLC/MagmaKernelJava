package com.meti.point;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class DereferenceParser implements Parser {
	private Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.startsWith("*")) {
			String dereferenceValue = trim.substring(1);
			Node child = compiler.parseSingle(dereferenceValue);
			return Optional.of(new DereferenceNode(child));
		}
		return Optional.empty();
	}

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return parse(value, compiler).stream().collect(Collectors.toSet());
	}
}
