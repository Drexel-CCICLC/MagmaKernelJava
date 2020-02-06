package com.meti.node.array;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.node.Type;
import com.meti.node.array.type.PointerArrayType;

import java.util.Optional;

public class ArrayResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		String trim = content.trim();
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
