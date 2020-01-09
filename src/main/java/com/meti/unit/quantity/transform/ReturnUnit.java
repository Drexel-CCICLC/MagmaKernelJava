package com.meti.unit.quantity.transform;

import com.meti.compile.ComplexCompiler;
import com.meti.type.Type;
import com.meti.unit.CompoundUnit;

import java.util.Optional;

public class ReturnUnit implements CompoundUnit {
	@Override
	public boolean canCompile(String value) {
		return value.startsWith("return");
	}

	@Override
	public String compile(String value, ComplexCompiler compiler) {
		String substring = value.substring(6);
		return "return " + compiler.compileOnly(substring) + ";";
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
