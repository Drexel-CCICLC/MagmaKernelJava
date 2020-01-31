package com.meti.struct;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;
import com.meti.exception.ParseException;

import java.util.Optional;

public class ReturnParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) throws ParseException {
		String trim = content.trim();
		if (trim.startsWith("return ")) {
			String valueString = trim.substring(7);
			Node value = compiler.parse(valueString);
			return Optional.of(new ReturnNode(value));
		}
		return Optional.empty();
	}
}
