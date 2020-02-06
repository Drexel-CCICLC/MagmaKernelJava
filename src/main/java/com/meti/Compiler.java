package com.meti;

import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.Type;

public interface Compiler {
	Node parse(String value) throws ParseException;

	Type resolveName(String name);

	Type resolveValue(String value);
}
