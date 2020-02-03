package com.meti.node.primitive;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.node.Type;

import java.util.Optional;

public class StringResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		String trim = content.trim();
		if (trim.equals("String")) {
			return Optional.of(StringType.INSTANCE);
		}
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		return Optional.empty();
	}
}
