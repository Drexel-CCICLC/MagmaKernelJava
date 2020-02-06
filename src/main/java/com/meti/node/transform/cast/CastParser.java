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
			String nameString = trim.substring(1, indexToSplit + 1);
			Type type = compiler.resolveName(nameString);
			String valueString = trim.substring(indexToSplit + 2);
			Node value = compiler.parse(valueString);
			return Optional.of(new CastNode(type, value));
		}
		return Optional.empty();
	}
}
