package com.meti.unit;

import com.meti.Compiler;

import java.util.Optional;

public class PrimitiveUnit implements Unit {
	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		if (trimmedInput.equals("true") || trimmedInput.equals("false")) {
			return Optional.of(trimmedInput);
		}

		try {
			Integer.parseInt(trimmedInput);
			return Optional.of(trimmedInput);
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}
}
