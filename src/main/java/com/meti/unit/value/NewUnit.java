package com.meti.unit.value;

import com.meti.Aliaser;
import com.meti.Compiler;
import com.meti.unit.Data;
import com.meti.unit.Declarations;
import com.meti.unit.Unit;

import java.util.Optional;
import java.util.stream.Collectors;

public class NewUnit implements Unit {
	private final Aliaser aliaser;
	private final Declarations declarations;

	public NewUnit(Data data) {
		this.aliaser = data.getAliaser();
		this.declarations = data.getDeclarations();
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		if (input.startsWith("new ")) {
			String value = input.substring(4);
			String functions = declarations.childrenOf(value)
					.stream()
					.map(aliaser::alias)
					.collect(Collectors.joining(","));
			return Optional.of("[" + functions + "]");
		}
		return Optional.empty();
	}
}
