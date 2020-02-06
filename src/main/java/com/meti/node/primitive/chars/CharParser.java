package com.meti.node.primitive.chars;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.exception.ParseException;
import com.meti.node.Node;

import java.util.Optional;

public class CharParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) throws ParseException {
		String trim = content.trim();
		if (trim.startsWith("'") && trim.endsWith("'")) {
			if (3 == trim.length()) {
				return Optional.of(new CharNode(trim.charAt(1)));
			} else {
				throw new ParseException(trim + " has too many characters.");
			}
		}
		return Optional.empty();
	}
}
