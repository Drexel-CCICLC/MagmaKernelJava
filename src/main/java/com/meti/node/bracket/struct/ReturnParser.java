package com.meti.node.bracket.struct;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Parser;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReturnParser implements Parser {
	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return parse(value, compiler).stream().collect(Collectors.toSet());
	}

	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		return Optional.of(value)
				.map(String::trim)
				.filter(trim -> trim.startsWith("return "))
				.map(trim -> trim.substring(7))
				.map(compiler::parseSingle)
				.map(ReturnNode::new);
	}
}
