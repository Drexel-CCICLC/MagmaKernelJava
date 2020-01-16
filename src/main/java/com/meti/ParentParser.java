package com.meti;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class ParentParser implements Parser {
	protected final Collection<Parser> children;

	public ParentParser(Parser... children) {
		this(Arrays.asList(children));
	}

	public ParentParser(Collection<Parser> children) {
		this.children = children;
	}

	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return children.stream()
				.map(child -> child.parseMultiple(value, compiler))
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}
}
