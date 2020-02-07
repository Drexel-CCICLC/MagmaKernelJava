package com.meti.node.array;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.exception.ParseException;
import com.meti.node.Type;
import com.meti.node.array.type.IndexedArrayType;

import java.util.Optional;

public class ArrayContentResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		return Optional.of(content)
				.map(String::trim)
				.map(s -> parse(s, compiler));
	}

	private Type parse(String trim, Compiler compiler) {
		int first = trim.indexOf('<');
		int last = trim.indexOf('>');
		if (-1 != first && -1 != last) return buildType(trim, compiler, first, last);
		throw new ParseException(trim + " has an invalid type argument.");
	}

	private Type buildType(String trim, Compiler compiler, int first, int last) {
		String typeString = trim.substring(first + 1, last);
		String trimmedType = typeString.trim();
		Type elementType = compiler.resolveName(trimmedType);
		return new IndexedArrayType(elementType);
	}
}
