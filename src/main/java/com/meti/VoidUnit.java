package com.meti;

import java.util.Optional;

public class VoidUnit implements Unit {
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
		return Optional.of("void").filter(s -> value.trim().equals("void")).map(Type::new);
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.empty();
	}
}
