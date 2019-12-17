package com.meti.unit;

import com.meti.Compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class InvocationUnit implements Unit {
	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		if (input.endsWith(")")) {
			int open = input.indexOf('(');
			int close = input.lastIndexOf(')');
			String caller = input.substring(0, open);
			String content = input.substring(open + 1, close);
			Collection<String> partitions = new ArrayList<>();
			StringBuilder builder = new StringBuilder();
			int depth = 0;
			for (char c : content.toCharArray()) {
				if (c == ',' && depth == 0) {
					partitions.add(builder.toString());
					builder = new StringBuilder();
				} else {
					if (c == '(') {
						depth++;
					} else if (c == ')') {
						depth--;
					}
					builder.append(c);
				}
			}
			partitions.add(builder.toString());
			String contentString = partitions.stream()
					.filter(value -> !value.isBlank())
					.map(compiler::compile)
					.collect(Collectors.joining(","));
			return Optional.of(compiler.compile(caller) + "(" + contentString + ")");
		}
		return Optional.empty();
	}
}
