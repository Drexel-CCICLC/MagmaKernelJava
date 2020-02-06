package com.meti.node.point;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class ReferenceParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		String trim = content.trim();
		if (trim.startsWith("&")) {
			String valueString = trim.substring(1);
			Node node = compiler.parse(valueString);
			return Optional.of(new ReferenceNode(node));
		}
		return Optional.empty();
	}
}
