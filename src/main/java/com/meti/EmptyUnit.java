package com.meti;

import java.util.Optional;

public class EmptyUnit implements CompoundUnit {
	@Override
	public boolean canCompile(String value) {
		return value.isBlank();
	}

	@Override
	public String compile(String value, Compiler compiler) {
		return value;
	}

	@Override
	public Optional<? extends Type> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.empty();
	}
}
