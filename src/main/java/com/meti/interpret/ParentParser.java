package com.meti.interpret;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Parser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class ParentParser implements Parser {
	private final Collection<? extends Parser> children;

	public ParentParser(Parser... children) {
		this(Arrays.asList(children));
	}

	protected ParentParser(Collection<? extends Parser> children) {
		this.children = children;
	}

	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		return children.stream()
				.map(child -> child.parse(value, compiler))
				.filter(Optional::isPresent)
				.findFirst()
				.orElseThrow();
	}
}
