package com.meti.node.array;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.exception.ParseException;
import com.meti.node.Type;

import java.util.Optional;

public class ArrayContentResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		String trim = content.trim();
		if (trim.startsWith("Array")) {
			int first = trim.indexOf('<');
			int last = trim.indexOf('>');
			if (-1 == first || -1 == last) {
				throw new ParseException(trim + " has an invalid type argument.");
			} else {
				String typeString = trim.substring(first + 1, last).trim();
				Type elementType = compiler.resolveName(typeString);
				return Optional.of(new IndexedArrayType(elementType));
			}
		}
		return Optional.empty();
	}
}
