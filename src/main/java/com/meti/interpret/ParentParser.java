package com.meti.interpret;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Parser;

import java.util.Arrays;
import java.util.Collection;

public class ParentParser implements Parser {
	private final Collection<Parser> children;

	public ParentParser(Parser... children) {
		this(Arrays.asList(children));
	}

	protected ParentParser(Collection<Parser> children) {
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
