package com.meti;

import java.util.Optional;

public class IntUnit implements Unit {
	@Override
	public boolean canCompile(String value) {
		boolean isInt;
		try {
			Integer.parseInt(value);
			isInt = true;
		} catch (Exception e) {
			isInt = false;
		}
		return isInt;
	}

	@Override
	public String compile(String value, Compiler compiler) {
		return value;
	}

	@Override
	public Optional<? extends Type> resolveName(String value, Compiler compiler) {
		return Optional.of("int").filter(s -> value.trim().equals("int")).map(BuildableType::new);
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.of("int").filter(s -> canCompile(value.trim())).map(BuildableType::new);
	}
}
