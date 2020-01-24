package com.meti;

import java.util.Optional;

public interface Resolver {
	Optional<Type> resolveValue(String content);
}
