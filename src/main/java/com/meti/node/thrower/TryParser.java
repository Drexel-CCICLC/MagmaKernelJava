package com.meti.node.thrower;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class TryParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		String trim = content.trim();
		if (trim.startsWith("try")) {
			String blockString = trim.substring(3);
			Node node = compiler.parse(blockString);
			return Optional.of(node);
		}
		return Optional.empty();
	}
}
