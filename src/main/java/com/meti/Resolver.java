package com.meti;

import java.util.Optional;

public interface Resolver {
	Optional<Type> resolveName(String name, Compiler compiler);

	Optional<Type> resolveValue(String value, Compiler compiler);
}
