package com.meti.resolve;

import com.meti.compile.Compiler;
import com.meti.type.Type;

import java.util.Optional;

public interface Resolver {
	Optional<? extends Type> resolveName(String value, Compiler compiler);

	Optional<Type> resolveValue(String value, Compiler compiler);
}
