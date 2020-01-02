package com.meti;

import java.util.Collection;

public interface Assembler {
	Collection<Node> assembleMultiple(String value);

	Node assembleSingle(String value);

	Type resolveName(String name);

	Type resolveValue(String value);
}
