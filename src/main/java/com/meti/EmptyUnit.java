package com.meti;

import java.util.Optional;

public class EmptyUnit implements Unit {
	@Override
	public boolean canCompile(String value) {
		return value.isBlank();
	}

	@Override
	public String compile(String value, Compiler compiler) {
		return value;
	}

	@Override
	public Optional<String> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<String> resolveValue(String value, Compiler compiler) {
		return Optional.empty();
	}
}
