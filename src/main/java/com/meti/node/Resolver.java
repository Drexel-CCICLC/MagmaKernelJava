package com.meti.node;

import com.meti.compile.Compiler;

import java.util.Optional;

public interface Resolver {
	Optional<Type> resolveName(String name, Compiler compiler);

	Optional<Type> resolveValue(String value, Compiler compiler);
}
