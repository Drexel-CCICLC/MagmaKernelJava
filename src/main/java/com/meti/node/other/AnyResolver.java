package com.meti.node.other;

import com.meti.compile.Compiler;
import com.meti.node.Resolver;
import com.meti.node.Type;

import java.util.Optional;

public class AnyResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String name, Compiler compiler) {
		String trim = name.trim();
		if ("Any".equals(trim)) return Optional.of(AnyType.INSTANCE);
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.empty();
	}
}
