package com.meti.node.value.primitive.array;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Parser;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class ArrayDeleteParser implements Parser {
	private static final String HEADER = "delete ";

	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		Collection<Node> nodes = parseMultiple(value, compiler);
		Object[] array = nodes.toArray();
		return 1 == array.length ?
				Optional.of((Node) array[0]) :
				Optional.empty();
	}

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
