package com.meti.unit;

import com.meti.compile.Compiler;
import com.meti.type.Type;

import java.util.Optional;

public class OperationUnit implements CompoundUnit {
	@Override
	public boolean canCompile(String value) {
		return value.contains("+");
	}

	@Override
	public String compile(String value, Compiler compiler) {
		int operation = value.indexOf('+');
		String first = value.substring(0, operation);
		String last = value.substring(operation + 1);
		return compiler.compileOnly(first) + "+" + compiler.compileOnly(last);
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
