package com.meti.node.primitive.strings;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.node.Type;

import java.util.Optional;

public class StringResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		return Optional.of(content)
				.map(String::trim)
				.filter("String"::equals)
				.map(s -> StringType.INSTANCE);
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		return Optional.of(content)
				.map(String::trim)
				.filter(s -> s.startsWith("\""))
				.filter(s -> s.endsWith("\""))
				.map(s -> StringType.INSTANCE);
	}
}
