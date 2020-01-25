package com.meti;

import java.util.Optional;

public class ParentResolver implements Resolver {
	private final Resolver[] children;

	public ParentResolver(Resolver... children) {
		this.children = children;
	}

	@Override
	public Optional<Type> resolveName(String content) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String content) {
		for (Resolver child : children) {
			Optional<Type> optional = child.resolveValue(content);
			if (optional.isPresent()) {
				return optional;
			}
		}
		return Optional.empty();
	}
}
