package com.meti.node.transform;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class NotParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		String trim = content.trim();
		if (trim.startsWith("!")) {
			String valueString = trim.substring(1);
			Node value = compiler.parse(valueString);
			return Optional.of(new NotNode(value));
		}
		return Optional.empty();
	}
}
