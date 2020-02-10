package com.meti.node.primitive.chars;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.exception.ParseException;
import com.meti.node.Type;

import java.util.Optional;

public class CharResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		return Optional.of(content)
				.map(String::trim)
				.filter("Char"::equals)
				.map(s -> CharType.INSTANCE);
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		return Optional.of(content)
				.map(String::trim)
				.filter(s -> s.startsWith("'"))
				.filter(s -> s.endsWith("'"))
				.map(this::buildType);
	}

	private Type buildType(String trim) {
		Type type;
		if (3 == trim.length()) {
			type = CharType.INSTANCE;
		} else {
			throw new ParseException(trim + " has too many characters.");
		}
		return type;
	}
}
