package com.meti.node.primitive.chars;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.exception.ParseException;
import com.meti.node.Node;

import java.util.Optional;

public class CharParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) throws ParseException {
		return Optional.of(content)
				.map(String::trim)
				.filter(s -> s.startsWith("'"))
				.filter(s -> s.endsWith("'"))
				.map(this::buildNode);
	}

	private Node buildNode(String trim) {
		if (3 == trim.length()) {
			return new CharNode(trim.charAt(1));
		} else {
			throw new ParseException(trim + " has too many characters.");
		}
	}
}
