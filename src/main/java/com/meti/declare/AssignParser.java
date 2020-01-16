package com.meti.declare;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;

import java.util.Optional;

public class AssignParser implements Parser {
	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.contains("=")) {
			int equals = trim.indexOf('=');
			String value0 = trim.substring(0, equals);
			String value1 = trim.substring(equals + 1);
			Node node0 = compiler.parse(value0);
			Node node1 = compiler.parse(value1);
			return Optional.of(new AssignNode(node0, node1));
		}
		return Optional.empty();
	}
}
