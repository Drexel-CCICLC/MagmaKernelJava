package com.meti.point;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;

import java.util.Optional;

public class ReferenceParser implements Parser {
	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.startsWith("&")) {
			String pointedValue = value.substring(1);
			Node child = compiler.parse(pointedValue);
			return Optional.of(new ReferenceNode(child));
		}
		return Optional.empty();
	}
}
