package com.meti.node.value.primitive.point;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Parser;

import java.util.Optional;

public class DereferenceParser implements Parser {
	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.startsWith("*")) {
			String dereferenceValue = trim.substring(1);
			Node child = compiler.parseSingle(dereferenceValue);
			return Optional.of(new DereferenceNode(child));
		}
		return Optional.empty();
	}

}
