package com.meti.compile;

import com.meti.type.Type;

public interface ComplexCompiler extends Compiler {
	Type resolveName(String value);

	Type resolveValue(String value);
}
