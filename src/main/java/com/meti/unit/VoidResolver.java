package com.meti.unit;

import com.meti.compile.ComplexCompiler;
import com.meti.resolve.Resolver;
import com.meti.type.Type;
import com.meti.type.VoidType;

import java.util.Optional;

public class VoidResolver implements Resolver {
	@Override
	public Optional<? extends Type> resolveName(String value, ComplexCompiler compiler) {
		return Optional.of("void")
				.filter(s -> value.trim().equals("void"))
				.map(s -> new VoidType());
	}

	@Override
	public Optional<Type> resolveValue(String value, ComplexCompiler compiler) {
		return Optional.empty();
	}

}
