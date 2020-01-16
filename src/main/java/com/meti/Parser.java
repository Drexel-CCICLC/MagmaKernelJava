package com.meti;

import java.util.Collection;
import java.util.Optional;

public interface Parser {
	@Deprecated
	Optional<Node> parse(String value, Compiler compiler);

	Collection<Node> parseMultiple(String value, Compiler compiler);
}
