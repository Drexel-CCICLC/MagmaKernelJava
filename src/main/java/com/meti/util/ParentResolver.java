package com.meti.util;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.node.Type;

import java.util.Optional;

public class ParentResolver implements Resolver {
	private final Resolver[] children;

	public ParentResolver(Resolver... children) {
		this.children = children;
	}

	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		for (Resolver child : children) {
			Optional<Type> optional = child.resolveName(content, compiler);
			if (optional.isPresent()) {
				return optional;
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		for (Resolver child : children) {
			Optional<Type> optional = child.resolveValue(content, compiler);
			if (optional.isPresent()) {
				return optional;
			}
		}
		return Optional.empty();
	}
}
