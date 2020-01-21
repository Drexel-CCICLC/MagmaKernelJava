package com.meti.interpret;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Parser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
		Collection<Node> nodes = parseMultiple(value, compiler);
		Object[] array = nodes.toArray();
		return 1 == array.length ?
				Optional.of((Node) array[0]) :
				Optional.empty();
	}

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		Optional<Node> result = children.stream()
				.map(child -> child.parse(value, compiler))
				.filter(Optional::isPresent)
				.findFirst()
				.orElseThrow();
		return (result.isEmpty()) ? Collections.emptySet() : Collections.singleton(result.get());
	}
}
