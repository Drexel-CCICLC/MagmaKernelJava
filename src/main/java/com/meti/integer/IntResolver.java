package com.meti.integer;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.Type;

import java.util.Optional;

public class IntResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String name, Compiler compiler) {
		if ("Int".equals(name.trim())) return Optional.of(new IntType());
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		try {
			Integer.parseInt(value.trim());
			return Optional.of(new IntType());
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}
}
