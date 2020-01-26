package com.meti;

import java.util.Optional;

public interface Resolver {
	Optional<Type> resolveName(String content);

	Optional<Type> resolveValue(String content, Compiler compiler);
}
