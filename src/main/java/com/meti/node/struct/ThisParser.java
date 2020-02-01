package com.meti.node.struct;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;
import com.meti.node.declare.Declarations;

import java.util.Optional;

public class ThisParser implements Parser {
	private final Declarations declarations;

	public ThisParser(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		return Optional.empty();
	}
}
