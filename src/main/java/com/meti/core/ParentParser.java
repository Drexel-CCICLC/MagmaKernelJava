package com.meti.core;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.exception.ParseException;
import com.meti.node.Node;

import java.util.Optional;

public class ParentParser implements Parser {
	private final Parser[] parsers;

	public ParentParser(Parser... parsers) {
		this.parsers = parsers;
	}

	@Override
	public Optional<Node> parse(String content, Compiler compiler) throws ParseException {
		for (Parser parser : parsers) {
			Optional<Node> parse = parser.parse(content, compiler);
			if (parse.isPresent()) {
				return parse;
			}
		}
		return Optional.empty();
	}
}
