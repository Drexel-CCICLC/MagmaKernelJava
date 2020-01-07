package com.meti;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlockUnit implements Unit {
	@Override
	public boolean canCompile(String value) {
		return value.startsWith("{");
	}

	@Override
	public String compile(String value, Compiler compiler) {
		String content = value.substring(1, value.length() - 1);
		String collect = Arrays.stream(content.split(";"))
				.map(compiler::compileOnly)
				.collect(Collectors.joining());
		return "{" + collect + "}";
	}

	@Override
	public Optional<Type> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.empty();
	}
}
