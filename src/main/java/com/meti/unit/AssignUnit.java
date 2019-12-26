package com.meti.unit;

import com.meti.Compiler;
import com.meti.DeclareManager;
import com.meti.exception.ImmutableException;
import com.meti.exception.TypeClashException;
import com.meti.type.Type;
import com.meti.type.TypeStack;

import java.util.Optional;

public class AssignUnit implements Unit {
	private final DeclareManager manager;
	private final TypeStack typeStack;

	public AssignUnit(Data data) {
		this.manager = data.getManager();
		this.typeStack = data.getTypeStack();
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		int equalsIndex = trimmedInput.indexOf('=');
		if (equalsIndex == -1) return Optional.empty();
		if (trimmedInput.charAt(equalsIndex + 1) == '=') return Optional.empty();
		String name = trimmedInput.substring(0, equalsIndex);
		String value = trimmedInput.substring(equalsIndex + 1).trim();
		return extractAssignment(compiler, name, value);
	}

	Optional<String> extractAssignment(Compiler compiler, String name, String value) {
		if (manager.relative(name.trim()).isMutable()) {
			throw new ImmutableException(name + " is immutable.");
		}
		String compiledName = compiler.compile(name);
		String compiledValue = compiler.compile(value);
		Type expected = typeStack.poll();
		Type actual = typeStack.poll();
		if (!(expected.equals(actual))) {
			throw new TypeClashException(expected, actual);
		}
		return Optional.of(compiledName + "=" + compiledValue + ";");
	}
}