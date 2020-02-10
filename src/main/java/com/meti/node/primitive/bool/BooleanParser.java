package com.meti.node.primitive.bool;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class BooleanParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		return switch (content.trim()) {
			case "true" -> Optional.of(new BooleanNode(true));
			case "false" -> Optional.of(new BooleanNode(false));
			default -> Optional.empty();
		};
	}
}
