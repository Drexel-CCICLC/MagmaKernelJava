package com.meti.node;

import com.meti.compile.Compiler;

import java.util.Collection;
import java.util.Optional;

public interface Parser {
	Optional<Node> parse(String value, Compiler compiler);

	Collection<Node> parseMultiple(String value, Compiler compiler);
}
