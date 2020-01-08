package com.meti;

import java.util.Optional;

public class AssignUnit implements CompoundUnit {
	@Override
	public boolean canCompile(String value) {
		return value.contains("=");
	}

	@Override
	public String compile(String value, Compiler compiler) {
		int equals = value.indexOf('=');
		String firstString = value.substring(0, equals);
		String lastString = value.substring(equals + 1);
		String first = compiler.compileOnly(firstString);
		String last = compiler.compileOnly(lastString);
		return first + "=" + last + ";";
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
