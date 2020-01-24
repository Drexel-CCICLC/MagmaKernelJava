package com.meti;

import com.meti.exception.ParseException;

import java.util.Optional;

public class VariableParser implements Parser {
	private final Declarations declarations;

	public VariableParser(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		String trim = content.trim();
		return declarations.get(trim)
				.map(Declaration::getName)
				.map(VariableNode::new);
	}
}
