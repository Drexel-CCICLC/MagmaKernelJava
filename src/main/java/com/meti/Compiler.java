package com.meti;

import com.meti.exception.ParseException;

public interface Compiler {
	Node parse(String value) throws ParseException;

	Type resolveName(String name);

	Type resolveValue(String value);
}
