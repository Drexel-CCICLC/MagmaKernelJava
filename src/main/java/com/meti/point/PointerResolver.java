package com.meti.point;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.Type;

import java.util.Optional;

public class PointerResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String name, Compiler compiler) {
		String trim = name.trim();
		if (trim.endsWith("*")) {
			String value = name.substring(0, name.length() - 1);
			Type child = compiler.resolveName(value);
			return Optional.of(new PointerType(child));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.empty();
	}
}
