package com.meti.unit;

import com.meti.compile.ComplexCompiler;
import com.meti.type.BuildableType;
import com.meti.type.Type;

import java.util.Optional;

public class PointerUnit implements CompoundUnit {
	@Override
	public boolean canCompile(String value) {
		return false;
	}

	@Override
	public String compile(String value, ComplexCompiler compiler) {
		return null;
	}

	@Override
	public Optional<? extends Type> resolveName(String value, ComplexCompiler compiler) {
		String trim = value.trim();
		if (trim.startsWith("~")) {
			Type type = compiler.resolveName(trim.substring(1));
			return Optional.of(new BuildableType(trim, null, type));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, ComplexCompiler compiler) {
		return Optional.empty();
	}
}
