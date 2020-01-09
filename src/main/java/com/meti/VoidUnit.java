package com.meti;

import java.util.Optional;

public class VoidUnit implements CompoundUnit {
	@Override
	public boolean canCompile(String value) {
		return false;
	}

	@Override
	public String compile(String value, Compiler compiler) {
		return null;
	}

	@Override
	public Optional<? extends Type> resolveName(String value, Compiler compiler) {
		return Optional.of("void")
				.filter(s -> value.trim().equals("void"))
				.map(s -> new VoidType());
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.empty();
	}

}
