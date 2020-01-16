package com.meti.array;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;

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
