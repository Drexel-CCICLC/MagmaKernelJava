package com.meti;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BlockUnit implements Unit {
	@Override
	public boolean canCompile(String trim) {
		return trim.startsWith("{");
	}

	@Override
	public String compile(String trim, Compiler compiler) {
		String content = trim.substring(1, trim.length() - 1);
		String collect = Arrays.stream(content.split(";"))
				.map(compiler::compileOnly)
				.collect(Collectors.joining());
		return "{" + collect + "}";
	}
}
