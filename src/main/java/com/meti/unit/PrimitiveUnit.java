package com.meti.unit;

import com.meti.Compiler;

import java.util.Optional;

public class PrimitiveUnit implements Unit {
	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		try {
			Integer.parseInt(input);
			return Optional.of(input);
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}
}
