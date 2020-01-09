package com.meti.unit.quantity.value;

import com.meti.compile.ComplexCompiler;
import com.meti.type.BuildableType;
import com.meti.type.Type;
import com.meti.unit.CompoundUnit;

import java.util.Optional;

public class StringUnit implements CompoundUnit {
	@Override
	public boolean canCompile(String value) {
		String trim = value.trim();
		boolean starts = trim.startsWith("\"");
		boolean ends = trim.endsWith("\"");
		return starts && ends;
	}

	@Override
	public String compile(String value, ComplexCompiler compiler) {
		return value.trim();
	}

	@Override
	public Optional<? extends Type> resolveName(String value, ComplexCompiler compiler) {
		return Optional.of("char*").filter(s -> value.trim().equals("string")).map(BuildableType::new);
	}

	@Override
	public Optional<Type> resolveValue(String value, ComplexCompiler compiler) {
		return Optional.of("char*").filter(s -> canCompile(value)).map(BuildableType::new);
	}
}
