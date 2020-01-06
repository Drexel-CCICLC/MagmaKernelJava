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
	public Optional<String> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<String> resolveValue(String value, Compiler compiler) {
		return Optional.empty();
	}
}
