package com.meti.unit.value;

import com.meti.Aliaser;
import com.meti.Compiler;
import com.meti.Declarations;
import com.meti.exception.DoesNotExistException;
import com.meti.type.Type;
import com.meti.type.TypeStack;
import com.meti.unit.Data;
import com.meti.unit.Declaration;
import com.meti.unit.Unit;

import java.util.*;

public class VariableUnit implements Unit {
	private final Aliaser aliaser;
	private final TypeStack stack;
	private final Declarations manager;

	public VariableUnit(Data data) {
		this.stack = data.getTypeStack();
		this.aliaser = data.getAliaser();
		this.manager = data.getManager();
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String name = input.trim();
		Optional<Declaration> found = manager.relativeOptionally(name);
		if (found.isPresent()) {
			stack.add(found.get().type());
			return Optional.ofNullable(aliaser.alias(name));
		} else if (input.indexOf('.') == -1) {
			throw notDefined(name);
		} else {
			String[] args = input.split("\\.");
			Deque<String> list = new LinkedList<>(List.of(args));
			Declaration value = manager.absolute(list);
			if (value.isNative()) {
				return Optional.of(name);
			} else {
				String child = list.removeLast();
				Declaration parent = manager.absolute(list);
				int order = parent.order(child).orElseThrow(() -> notDefined(name));
				return Optional.ofNullable(compiler.compile(String.join(".", list)) + "[" + order + "]");
			}
		}
	}

	private DoesNotExistException notDefined(String name) {
		return new DoesNotExistException(name + " is not defined.");
	}

	@Override
	public Optional<Type> resolve(String input, Compiler compiler) {
		return Optional.empty();
	}
}
