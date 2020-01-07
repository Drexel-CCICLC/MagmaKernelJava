package com.meti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlockUnit implements Unit {
	@Override
	public boolean canCompile(String value) {
		String trim = value.trim();
		return trim.startsWith("{") && trim.endsWith("}");
	}

	@Override
	public String compile(String value, Compiler compiler) {
		String content = value.substring(1, value.length() - 1);
		Collection<String> partitions = new ArrayList<>();
		StringBuilder current = new StringBuilder();
		int depth = 0;
		for (char c : content.toCharArray()) {
			if(c == ';' && depth == 0) {
				partitions.add(current.toString());
				current = new StringBuilder();
			} else {
				if(c ==  '{'){
					depth++;
				} else if(c == '}') {
					depth--;
				}
				current.append(c);
			}
		}
		partitions.add(current.toString());
		String collect = partitions.stream()
				.filter(string -> !string.isBlank())
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
