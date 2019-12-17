package com.meti.unit;

import com.meti.Compiler;
import com.meti.exception.ParseException;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class UnitCompiler implements Compiler {
	private final Unit root;

	public UnitCompiler(Unit root) {
		this.root = root;
	}

	@Override
	public String compile(String input) {
		if (input.contains(";")) {
			return Arrays.stream(input.split(";"))
					.map(this::compile)
					.collect(Collectors.joining());
		} else {
			Optional<String> toReturn = root.parse(input, null);
			if (toReturn.isEmpty()) {
				throw new ParseException("Failed to parse \"" + input + "\".");
			}
			return toReturn.get();
		}
	}
}