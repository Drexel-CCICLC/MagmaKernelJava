package com.meti.node.struct;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class ReturnParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		return Optional.of(content)
				.map(String::trim)
				.filter(s -> s.startsWith("return "))
				.map(s -> s.substring(7))
				.map(compiler::parse)
				.map(ReturnNode::new);
	}
}
