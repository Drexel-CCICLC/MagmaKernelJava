package com.meti.node.primitive.bool;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class BooleanParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		String trim = content.trim();
		if ("true".equals(trim)) {
			return Optional.of(new BooleanNode(true));
		} else if ("false".equals(trim)) {
			return Optional.of(new BooleanNode(false));
		}
		return Optional.empty();
	}
}
