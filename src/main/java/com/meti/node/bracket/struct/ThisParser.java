package com.meti.node.bracket.struct;

import com.meti.compile.Compiler;
import com.meti.declare.Declarations;
import com.meti.node.Node;
import com.meti.node.Parser;

import java.util.Optional;

public class ThisParser implements Parser {
	private final Declarations declarations;

	public ThisParser(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.equals("this")) {
			return Optional.of(declarations.current().toInstance());
		}
		return Optional.empty();
	}
}
