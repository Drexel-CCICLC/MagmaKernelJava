package com.meti.array;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;
import com.meti.exception.ParseException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArrayContentParser implements Parser {
	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		//TODO: provide implementation
		String trim = value.trim();
		if (trim.startsWith("Array")) {
			int typeEnd = trim.indexOf('>');
			String contentString = trim.substring(typeEnd + 1).trim();
			if (contentString.startsWith("{") && contentString.endsWith("}")) {
				String content = contentString.substring(1, contentString.length() - 1);
				List<Node> list = Arrays.stream(content.split(","))
						.map(compiler::parse)
						.collect(Collectors.toList());
				return Optional.of(new ArrayContentNode(list));
			} else {
				throw new ParseException(trim + " does not have content surrounded in curly braces.");
			}
		}
		return Optional.empty();
	}
}
