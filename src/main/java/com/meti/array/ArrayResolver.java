package com.meti.array;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.Type;

import java.util.Optional;

public class ArrayResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String name, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.startsWith("Array")) {
			int typeStart = trim.indexOf('<') + 1;
			int typeEnd = trim.indexOf('>');
			String typeString = trim.substring(typeStart, typeEnd);
			Type type = compiler.resolveName(typeString);
			return Optional.of(new PointerArrayType(type));
		}
		return Optional.empty();
	}
}
