package com.meti;

import java.util.Collection;

public interface Compiler {
	@Deprecated
	Node parseSingle(String value);

	Collection<Node> parseMultiple(String value);

	Type resolveName(String name);

	Type resolveValue(String value);
}
