package com.meti.node.thrower;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class TryParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		return Optional.of(content)
				.map(String::trim)
				.filter(s -> s.startsWith("try"))
				.map(s -> s.substring(3))
				.map(compiler::parse);
	}
}
