package com.meti;

import java.util.Optional;

public class StructUnit implements Unit {
	@Override
	public boolean canCompile(String value) {
		return value.contains(":");
	}

	@Override
	public String compile(String value, Compiler compiler) {
		return null;
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
