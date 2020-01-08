package com.meti;

import java.util.Optional;

public class StringUnit implements Unit {
	@Override
	public boolean canCompile(String value) {
		String trim = value.trim();
		return trim.startsWith("\"") && trim.endsWith("\"");
	}

	@Override
	public String compile(String value, Compiler compiler) {
		return value.trim();
	}

	@Override
	public Optional<? extends Type> resolveName(String value, Compiler compiler) {
		return Optional.of("char*").filter(s -> value.trim().equals("string")).map(BuildableType::new);
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.of("char*").filter(s -> canCompile(value)).map(BuildableType::new);
	}
}