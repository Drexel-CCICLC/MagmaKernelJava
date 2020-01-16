package com.meti.character;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.ParseException;
import com.meti.Parser;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class CharParser implements Parser {
	private Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.startsWith("'") && trim.endsWith("'")) {
			String charString = trim.substring(1, trim.length() - 1);
			if (3 == charString.length()) {
				return Optional.of(new CharNode(charString.charAt(1)));
			} else {
				throw new ParseException("Too many characters.");
			}
		}
		return Optional.empty();
	}

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return parse(value, compiler).stream().collect(Collectors.toSet());
	}
}
