package com.meti.unit.value;

import com.meti.Compiler;
import com.meti.unit.Unit;

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
			try {
				Double.parseDouble(trimmedInput);
				return Optional.of(trimmedInput);
			} catch (NumberFormatException e1) {
				return Optional.empty();
			}
		}
	}
}
