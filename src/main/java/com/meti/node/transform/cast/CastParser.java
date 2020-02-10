package com.meti.node.transform.cast;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;
import com.meti.node.Type;

import java.util.Optional;

public class CastParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		String trim = content.trim();
		if (trim.startsWith("<")) {
			int indexToSplit = findIndex(content);
			Type type = parseType(compiler, trim, indexToSplit);
			Node value = parseValue(compiler, trim, indexToSplit);
			return Optional.of(new CastNode(type, value));
		}
		return Optional.empty();
	}

	private int findIndex(String content) {
		int indexToSplit = -1;
		int depth = 0;
		char[] charArray = content.substring(1).toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if ('>' == c && 0 == depth) {
				indexToSplit = i;
			} else {
				if ('<' == c) depth++;
				if ('>' == c) depth--;
			}
		}
		return indexToSplit;
	}

	private Type parseType(Compiler compiler, String trim, int indexToSplit) {
		String nameString = trim.substring(1, indexToSplit + 1);
		return compiler.resolveName(nameString);
	}

	private Node parseValue(Compiler compiler, String trim, int indexToSplit) {
		String valueString = trim.substring(indexToSplit + 2);
		return compiler.parse(valueString);
	}
}
