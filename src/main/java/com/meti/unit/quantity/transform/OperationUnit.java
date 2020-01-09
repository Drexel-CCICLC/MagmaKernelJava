package com.meti.unit.quantity.transform;

import com.meti.compile.ComplexCompiler;
import com.meti.type.Type;
import com.meti.unit.CompoundUnit;

import java.util.Optional;

public class OperationUnit implements CompoundUnit {
	@Override
	public boolean canCompile(String value) {
		return value.contains("+");
	}

	@Override
	public String compile(String value, ComplexCompiler compiler) {
		int operation = value.indexOf('+');
		String first = value.substring(0, operation);
		String last = value.substring(operation + 1);
		return compiler.compileOnly(first) + "+" + compiler.compileOnly(last);
	}

	@Override
	public Optional<? extends Type> resolveName(String value, ComplexCompiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, ComplexCompiler compiler) {
		return Optional.empty();
	}
}
