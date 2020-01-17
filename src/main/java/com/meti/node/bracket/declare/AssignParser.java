package com.meti.node.bracket.declare;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Parser;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class AssignParser implements Parser {
	private Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.contains("=")) {
			int equals = trim.indexOf('=');
			String value0 = trim.substring(0, equals);
			String value1 = trim.substring(equals + 1);
			Node node0 = compiler.parseSingle(value0);
			Node node1 = compiler.parseSingle(value1);
			return Optional.of(new AssignNode(node0, node1));
		}
		return Optional.empty();
	}

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return parse(value, compiler).stream().collect(Collectors.toSet());
	}
}
