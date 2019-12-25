package com.meti.unit.value;

import com.meti.Compiler;
import com.meti.unit.Data;
import com.meti.unit.Unit;

import java.util.Optional;

import static com.meti.type.PrimitiveType.DOUBLE;
import static com.meti.type.PrimitiveType.INT;

public class PrimitiveUnit implements Unit {
	private final Data data;

	public PrimitiveUnit(Data data) {
		this.data = data;
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		if (trimmedInput.equals("true") || trimmedInput.equals("false")) {
			return Optional.of(trimmedInput);
		}
		try {
			Integer.parseInt(trimmedInput);
			data.getTypeStack().add(INT);
			return Optional.of(trimmedInput);
		} catch (NumberFormatException e) {
			try {
				Double.parseDouble(trimmedInput);
				data.getTypeStack().add(DOUBLE);
				return Optional.of(trimmedInput);
			} catch (NumberFormatException e1) {
				return Optional.empty();
			}
		}
	}
}
