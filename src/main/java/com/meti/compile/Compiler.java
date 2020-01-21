package com.meti.compile;

import com.meti.node.Node;
import com.meti.node.Type;

import java.util.Collection;

public interface Compiler {
	Node parseSingle(String value);

	Type resolveName(String name);

	Type resolveValue(String value);
}
