package com.meti.unit.value;

import com.meti.Aliaser;
import com.meti.Compiler;
import com.meti.Declarations;
import com.meti.type.Type;
import com.meti.type.TypeStack;
import com.meti.unit.Data;
import com.meti.unit.Declaration;
import com.meti.unit.Unit;

import java.util.Optional;
import java.util.stream.Collectors;

public class NewUnit implements Unit {
	private final Aliaser aliaser;
	private final Declarations manager;
	private final TypeStack typeStack;

	public NewUnit(Data data) {
		this.aliaser = data.getAliaser();
		this.manager = data.getManager();
		this.typeStack = data.getTypeStack();
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		if (input.startsWith("new")) {
			Declaration parent = manager.current();
			String functions = parent
					.children()
					.keySet()
					.stream()
					.map(aliaser::alias)
					.collect(Collectors.joining(","));
			ObjectType type = new ObjectType(parent);
			typeStack.add(type);
			return Optional.of("[" + functions + "]");
		}
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolve(String input, Compiler compiler) {
		return Optional.empty();
	}
}
