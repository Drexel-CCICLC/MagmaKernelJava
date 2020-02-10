package com.meti.node.point;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class ReferenceParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		return Optional.of(content)
				.map(String::trim)
				.filter(s -> s.startsWith("&"))
				.map(s -> s.substring(1))
				.map(compiler::parse)
				.map(ReferenceNode::new);
	}
}
