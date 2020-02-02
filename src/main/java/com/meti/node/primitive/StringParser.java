package com.meti.node.primitive;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class StringParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		String trim = content.trim();
		if (trim.startsWith("\"") && trim.endsWith("\"")) {
			return Optional.of(new StringNode(trim.substring(1, trim.length() - 1)));
		}
		return Optional.empty();
	}
}
