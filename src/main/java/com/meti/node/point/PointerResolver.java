package com.meti.node.point;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.node.Type;

import java.util.Optional;

public class PointerResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		String trim = content.trim();
		if (trim.endsWith("*")) {
			String childString = trim.substring(0, trim.length() - 1);
			Type child = compiler.resolveName(childString);
			return Optional.of(new PointerType(child));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		return Optional.empty();
	}
}
