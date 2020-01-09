package com.meti.resolve;

import com.meti.compile.ComplexCompiler;
import com.meti.type.Type;

import java.util.Optional;

public interface Resolver {
	Optional<? extends Type> resolveName(String value, ComplexCompiler compiler);

	Optional<Type> resolveValue(String value, ComplexCompiler compiler);
}
