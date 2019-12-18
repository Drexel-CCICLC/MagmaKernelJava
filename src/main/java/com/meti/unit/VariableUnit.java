package com.meti.unit;

import com.meti.Aliaser;
import com.meti.Compiler;
import com.meti.exception.DoesNotExistException;

import java.util.Optional;

public class VariableUnit implements Unit {
	private final Aliaser aliaser;
	private final Declarations declarations;

	public VariableUnit(Data data) {
		this.declarations = data.getDeclarations();
		this.aliaser = data.getAliaser();
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		for (char c : input.toCharArray()) {
			if (!Character.isLetter(c) && !Character.isDigit(c)) {
				return Optional.empty();
			}
		}

		if (declarations.isDefined(input)) {
			return declarations.hasFlag(input, "native") ?
					Optional.of(input) :
					Optional.of(aliaser.alias(input));
		}
		throw new DoesNotExistException(input + " is not defined.");
	}
}
