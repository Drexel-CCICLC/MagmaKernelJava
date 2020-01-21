package com.meti.node.value.primitive.point;

import com.meti.compile.Compiler;
import com.meti.node.Resolver;
import com.meti.node.Type;

import java.util.Optional;

public class PointerResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String name, Compiler compiler) {
		String trim = name.trim();
		if (trim.endsWith("*")) {
			String value = name.substring(0, name.length() - 1);
			Type child = compiler.resolveName(value);
			return Optional.of(PointerType.pointerOf(child));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.empty();
	}
}
