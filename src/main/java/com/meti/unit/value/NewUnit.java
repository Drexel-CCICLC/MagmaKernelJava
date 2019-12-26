package com.meti.unit.value;

import com.meti.Aliaser;
import com.meti.Compiler;
import com.meti.DeclareManager;
import com.meti.unit.Data;
import com.meti.unit.Unit;

import java.util.Optional;
import java.util.stream.Collectors;

public class NewUnit implements Unit {
	private final Aliaser aliaser;
	private final DeclareManager manager;

	public NewUnit(Data data) {
		this.aliaser = data.getAliaser();
		this.manager = data.getManager();
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		if (input.startsWith("new")) {
			String functions = manager.current()
					.children()
					.keySet()
					.stream()
					.map(aliaser::alias)
					.collect(Collectors.joining(","));
			return Optional.of("[" + functions + "]");
		}
		return Optional.empty();
	}
}
