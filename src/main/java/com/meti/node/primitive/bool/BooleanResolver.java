package com.meti.node.primitive.bool;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.node.Type;

import java.util.Optional;

public class BooleanResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		return Optional.of(content)
				.map(String::trim)
				.filter("Bool"::equals)
				.map(s -> BoolType.INSTANCE);
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		return Optional.empty();
	}
}
