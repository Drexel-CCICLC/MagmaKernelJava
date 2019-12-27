package com.meti.unit;

import com.meti.Compiler;
import com.meti.exception.ParseException;
import com.meti.type.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class UnitCompiler implements Compiler {
	private final Unit root;

	public UnitCompiler(Unit root) {
		this.root = root;
	}

	@Override
	public String compile(String input) {
		Collection<String> partitions = new ArrayList<>();
		StringBuilder current = new StringBuilder();
		int depth = 0;
		for (char c : input.toCharArray()) {
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
		return partitions.stream()
				.filter(s -> !s.isBlank())
				.map(s -> {
					Optional<String> parse;
					try {
						parse = root.parse(s, this);
					} catch (Exception e) {
						throw new ParseException("Failed to parse \"" + s + "\".", e);
					}
					return parse.orElseThrow(() -> new ParseException("Failed to parse \"" + s + "\"."));
				})
				.collect(Collectors.joining());
	}

	@Override
	public Type resolve(String value) {
		return root.resolve(value, this)
				.orElseThrow(() -> new ParseException("Could not resolve type: " + value));
	}
}