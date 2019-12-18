package com.meti.unit.value;

import com.meti.Aliaser;
import com.meti.Compiler;
import com.meti.exception.DoesNotExistException;
import com.meti.unit.Data;
import com.meti.unit.Declarations;
import com.meti.unit.Unit;

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
		String trimmedInput = input.trim();

		if(trimmedInput.indexOf('.') != -1) {
			int lastIndex = trimmedInput.lastIndexOf('.');
			String childName = input.substring(lastIndex + 1);
			String value = input.substring(0, lastIndex);
			return Optional.of(compiler.compile(value) + "[" + declarations.order(childName) + ']');
		}

		for (char c : trimmedInput.toCharArray()) {
			if (!Character.isLetter(c) && !Character.isDigit(c)) {
				return Optional.empty();
			}
		}

		if (declarations.isDefined(trimmedInput)) {
			return declarations.hasFlag("native", trimmedInput) ?
					Optional.of(trimmedInput) :
					Optional.of(aliaser.alias(trimmedInput));
		}
		throw new DoesNotExistException(trimmedInput + " is not defined.");
	}
}
