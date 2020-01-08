package com.meti;

import java.util.Optional;

class AnyResolver implements Resolver {
	@Override
	public Optional<? extends Type> resolveName(String value, Compiler compiler) {
        return Optional.of(AnyType.INSTANCE).filter(s -> value.trim().equals("Any"));
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.empty();
	}
}
