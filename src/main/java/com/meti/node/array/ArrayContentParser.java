package com.meti.node.array;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.exception.ParseException;
import com.meti.node.Node;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArrayContentParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		return Optional.of(content)
				.map(String::trim)
				.filter(s -> s.startsWith("Array"))
				.map(s -> buildNode(s, compiler));
	}

	private Node buildNode(String trim, Compiler compiler) {
		String content = parseContent(trim);
		if (isBlock(content)) {
			List<Node> list = parseContent(compiler, content);
			return new ArrayContentNode(list);
		} else {
			throw new ParseException(trim + " does not have content surrounded in curly braces.");
		}
	}

	private String parseContent(String trim) {
		int typeEnd = trim.indexOf('>');
		return trim.substring(typeEnd + 1).trim();
	}

	private boolean isBlock(String contentString) {
		return contentString.startsWith("{") && contentString.endsWith("}");
	}

	private List<Node> parseContent(Compiler compiler, String contentString) {
		String content = contentString.substring(1, contentString.length() - 1);
		return Arrays.stream(content.split(","))
				.map(compiler::parse)
				.collect(Collectors.toList());
	}
}
