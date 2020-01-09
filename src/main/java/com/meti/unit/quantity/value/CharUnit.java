package com.meti.unit.quantity.value;

import com.meti.compile.ComplexCompiler;
import com.meti.type.BuildableType;
import com.meti.type.Type;
import com.meti.unit.CompoundUnit;

import java.util.Optional;

public class CharUnit implements CompoundUnit {
	@Override
	public boolean canCompile(String value) {
		return value.startsWith("'") && value.endsWith("'");
	}

	@Override
	public String compile(String value, ComplexCompiler compiler) {
		return value;
	}

	@Override
	public Optional<? extends Type> resolveName(String value, ComplexCompiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, ComplexCompiler compiler) {
		return Optional.of("char")
				.filter(s -> canCompile(value))
				.map(BuildableType::new);
	}
}
