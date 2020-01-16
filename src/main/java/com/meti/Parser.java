package com.meti;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public interface Parser {
	default Optional<Node> parse(String value, Compiler compiler) {
		return parseMultiple(value, compiler).stream().findFirst();
	}

	default Collection<Node> parseMultiple(String value, Compiler compiler) {
		return Collections.emptySet();
	}
}
