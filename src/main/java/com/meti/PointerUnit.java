package com.meti;

import java.util.Optional;

public class PointerUnit implements Unit {
	@Override
	public boolean canCompile(String value) {
		return false;
	}

	@Override
	public String compile(String value, Compiler compiler) {
		return null;
	}

	@Override
	public Optional<Type> resolveName(String value, Compiler compiler) {
		if (value.startsWith("~")) {
			Type type = compiler.resolveName(value.substring(1));
			return Optional.of(new Type(value, null, type));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.empty();
	}
}
