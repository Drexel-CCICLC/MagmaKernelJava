package com.meti.unit.value;

import com.meti.Aliaser;
import com.meti.Compiler;
import com.meti.exception.DoesNotExistException;
import com.meti.unit.Data;
import com.meti.unit.Declarations;
import com.meti.unit.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class VariableUnit implements Unit {
	private final Aliaser aliaser;
	private final Declarations declarations;
	private final Stack<String> stack;

	public VariableUnit(Data data) {
		this.declarations = data.getDeclarations();
		this.aliaser = data.getAliaser();
		this.stack = data.getStack();
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();

		if (trimmedInput.indexOf('.') != -1) {
			int lastIndex = trimmedInput.lastIndexOf('.');
			String childName = trimmedInput.substring(lastIndex + 1);
			String value = trimmedInput.substring(0, lastIndex);
			if (Character.isDigit(childName.charAt(0))) {
				return Optional.empty();
			}
			String toAppend;
			if (declarations.hasAnyFlag("native", childName)) {
				toAppend = "." + childName;
			} else {
				toAppend = "[" + declarations.order(childName) + ']';
			}
			return Optional.of(compiler.compile(value) + toAppend);
		}

	/*	for (char c : trimmedInput.toCharArray()) {
			if (!Character.isLetter(c) && !Character.isDigit(c)) {
				return Optional.empty();
			}
		}
*/
		List<String> child = new ArrayList<>();
		if (!stack.isEmpty()) {
			child.addAll(stack.subList(0, stack.size() - 1));
		}
		child.add(trimmedInput);
		if (declarations.isInScope(child.toArray(String[]::new))) {
			return declarations.hasFlag("native", trimmedInput) ?
					Optional.of(trimmedInput) :
					Optional.of(aliaser.alias(trimmedInput));
		}
		throw new DoesNotExistException(trimmedInput + " is not defined.");
	}
}
