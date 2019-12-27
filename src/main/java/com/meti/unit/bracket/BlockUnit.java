package com.meti.unit.bracket;

import com.meti.Compiler;
import com.meti.type.Type;
import com.meti.unit.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlockUnit implements Unit {
	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		if (trimmedInput.startsWith("{") && trimmedInput.endsWith("}")) {
			List<String> partitions = new ArrayList<>();
			StringBuilder current = new StringBuilder();
			int depth = 0;
			for (char c : trimmedInput.substring(1, trimmedInput.length() - 1).toCharArray()) {
				if (c == ';' && depth == 0) {
					partitions.add(current.toString());
					current = new StringBuilder();
				} else {
					if (c == '{') {
						depth++;
					} else if (c == '}') {
						depth--;
					}
					current.append(c);
				}
			}
			partitions.add(current.toString());
			return Optional.of("{" + partitions.stream()
					.map(compiler::compile)
					.collect(Collectors.joining()) + "}");
		}
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolve(String input, Compiler compiler) {
		return Optional.empty();
	}
}
