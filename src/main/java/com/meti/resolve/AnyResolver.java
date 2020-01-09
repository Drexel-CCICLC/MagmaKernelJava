package com.meti.resolve;

import com.meti.compile.Compiler;
import com.meti.type.AnyType;
import com.meti.type.Type;

import java.util.Optional;

public class AnyResolver implements Resolver {
	@Override
	public Optional<? extends Type> resolveName(String value, Compiler compiler) {
        return Optional.of(AnyType.INSTANCE).filter(s -> value.trim().equals("Any"));
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.empty();
	}
}
