package com.meti.unit.value;

import com.meti.Compiler;
import com.meti.unit.Unit;

import java.util.Optional;

public class StringUnit implements Unit {
	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		if(trimmedInput.startsWith("\"") && trimmedInput.endsWith("\"")) {
			return Optional.of(trimmedInput);
		}
		return Optional.empty();
	}
}
