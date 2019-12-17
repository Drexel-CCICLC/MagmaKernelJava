package com.meti.unit;

import com.meti.Compiler;
import com.meti.Aliaser;
import com.meti.exception.DoesNotExistException;

import java.util.Optional;
import java.util.Set;

public class VariableUnit implements Unit {
	private final Aliaser aliaser;
	private final Set<String> declarations;

	public VariableUnit(Set<String> declarations, Aliaser aliaser) {
		this.declarations = declarations;
		this.aliaser = aliaser;
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		if (declarations.contains(input)) {
			return Optional.of(aliaser.alias(input));
		}
		throw new DoesNotExistException(input + " is not defined.");
	}
}
