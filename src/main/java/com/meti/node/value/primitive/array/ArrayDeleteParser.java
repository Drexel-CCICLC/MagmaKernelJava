package com.meti.node.value.primitive.array;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Parser;

import java.util.Optional;

public class ArrayDeleteParser implements Parser {
	private static final String HEADER = "delete ";

	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		return trim.startsWith(HEADER) ?
				Optional.of(buildNode(compiler, trim)) :
				Optional.empty();
	}

	private Node buildNode(Compiler compiler, String trim) {
		String valueString = trim.substring(HEADER.length());
		Node valueNode = compiler.parseSingle(valueString);
		return new ArrayDeleteNode(valueNode);
	}
}
