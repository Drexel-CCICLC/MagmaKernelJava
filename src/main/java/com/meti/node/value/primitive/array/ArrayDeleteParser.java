package com.meti.node.value.primitive.array;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Parser;

import java.util.Collection;
import java.util.Collections;

public class ArrayDeleteParser implements Parser {
	private static final String HEADER = "delete ";

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.startsWith(HEADER)) {
			Node node = buildNode(compiler, trim);
			return Collections.singleton(node);
		}
		return Collections.emptySet();
	}

	private Node buildNode(Compiler compiler, String trim) {
		String valueString = trim.substring(HEADER.length());
		Node valueNode = compiler.parseSingle(valueString);
		return new ArrayDeleteNode(valueNode);
	}
}
