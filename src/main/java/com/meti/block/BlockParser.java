package com.meti.block;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;
import com.meti.util.BracketPartitioner;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlockParser implements Parser {
	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return parse(value, compiler).stream().collect(Collectors.toSet());
	}

	private Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.startsWith("{") && trim.endsWith("}")) {
			String childrenString = trim.substring(1, trim.length() - 1);
            Collection<String> childrenStrings = BracketPartitioner.create().partition(childrenString);
			List<Node> children = childrenStrings.stream()
					.map(String::trim)
					.filter(childString -> !childString.isEmpty())
					.map(compiler::parseMultiple)
					.flatMap(Collection::stream)
					.collect(Collectors.toList());
			return Optional.of(new BlockNode(children));
		}
		return Optional.empty();
	}
}
