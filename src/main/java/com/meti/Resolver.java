package com.meti;

import java.util.Optional;

public interface Resolver {
	Optional<? extends Type> resolveName(String value, Compiler compiler);

	Optional<Type> resolveValue(String value, Compiler compiler);
}
