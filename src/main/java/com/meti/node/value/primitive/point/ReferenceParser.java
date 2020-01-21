package com.meti.node.value.primitive.point;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Parser;

import java.util.Optional;

public class ReferenceParser implements Parser {
	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.startsWith("&")) {
			String pointedValue = value.substring(1);
			Node child = compiler.parseSingle(pointedValue);
			return Optional.of(new ReferenceNode(child));
		}
		return Optional.empty();
	}

}
