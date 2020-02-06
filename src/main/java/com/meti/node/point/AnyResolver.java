package com.meti.node.point;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.node.Type;

import java.util.Optional;

public class AnyResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		String trim = content.trim();
		if (trim.equals("Any")) {
			return Optional.of(new AnyType());
		}
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		return Optional.empty();
	}
}
