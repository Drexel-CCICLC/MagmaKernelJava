package com.meti;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class InvocationUnit implements Unit {
	@Override
	public boolean canCompile(String value) {
		return value.endsWith(")");
	}

	@Override
	public String compile(String value, Compiler compiler) {
		int open = value.indexOf('(');
		String name = value.substring(0, open);
		String parameterString = value.substring(open + 1, value.length() - 1);
		String params = Arrays.stream(parameterString.split(","))
				.map(compiler::compileOnly)
				.collect(Collectors.joining(","));
		return name + "(" + params + ")";
	}

	@Override
	public Optional<String> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<String> resolveValue(String value, Compiler compiler) {
		return Optional.empty();
	}
}
