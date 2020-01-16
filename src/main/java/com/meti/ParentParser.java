package com.meti;

import java.util.Arrays;
import java.util.Collection;

public class ParentParser implements Parser {
	protected final Collection<Parser> children;

	public ParentParser(Parser... children) {
		this(Arrays.asList(children));
	}

	public ParentParser(Collection<Parser> children) {
		this.children = children;
	}

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return children.stream()
				.map(child -> child.parseMultiple(value, compiler))
				.filter(collection -> !collection.isEmpty())
				.findFirst()
				.orElseThrow();
	}
}
