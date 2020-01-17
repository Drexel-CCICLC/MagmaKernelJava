package com.meti.node.value.primitive.integer;

import com.meti.compile.Compiler;
import com.meti.node.Resolver;
import com.meti.node.Type;

import java.util.Optional;

public class IntResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String name, Compiler compiler) {
		if ("Int".equals(name.trim())) return Optional.of(IntType.INSTANCE());
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		try {
			Integer.parseInt(value.trim());
			return Optional.of(IntType.INSTANCE());
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}
}
