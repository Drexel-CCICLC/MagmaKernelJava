package com.meti.node.value.primitive.array;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Parser;

import java.util.Optional;

public class ArrayIndexParser implements Parser {
	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.contains("[") && trim.endsWith("]")) {
			int start = trim.indexOf('[');
			String arrayString = trim.substring(0, start);
			String indexString = trim.substring(start + 1, trim.length() - 1);
			Node array = compiler.parseSingle(arrayString);
			Node index = compiler.parseSingle(indexString);
			return Optional.of(new ArrayIndexNode(array, index));
		}
		return Optional.empty();
	}

}
