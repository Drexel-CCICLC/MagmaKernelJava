package com.meti;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public interface Parser {
	default Collection<Node> parseMultiple(String value, Compiler compiler) {
		return parse(value, compiler).stream().collect(Collectors.toSet());
	}

	@Deprecated
	Optional<Node> parse(String value, Compiler compiler);
}
