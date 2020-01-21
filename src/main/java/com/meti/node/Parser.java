package com.meti.node;

import com.meti.compile.Compiler;

import java.util.Collection;
import java.util.Optional;

public interface Parser {
	default Optional<Node> parse(String value, Compiler compiler) {
		Collection<Node> nodes = parseMultiple(value, compiler);
		Object[] array = nodes.toArray();
		return 1 == array.length ?
				Optional.of((Node) array[0]) :
				Optional.empty();
	}

	Collection<Node> parseMultiple(String value, Compiler compiler);
}
