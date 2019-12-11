package com.meti;

import java.util.Optional;
import java.util.Set;

public class Compiler {
	private final Set<Unit> units;

	public Compiler(Unit... units) {
		this(Set.of(units));
	}

	public Compiler(Set<Unit> units) {
		this.units = units;
	}

	public String compile(Node node) {
		return units.stream()
				.map(unit -> unit.compile(node, this))
				.flatMap(Optional::stream)
				.findAny()
				.orElseThrow(() -> fail("Failed to compile: " + node));
	}

	private IllegalStateException fail(String s) {
		return new IllegalStateException(s);
	}

	public Node parse(String value) {
		return units.stream()
				.map(unit -> unit.parse(value, this))
				.flatMap(Optional::stream)
				.findAny()
				.orElseThrow(() -> fail(value));
	}

	public Node transform(Node node) {
		return units.stream()
				.map(unit -> unit.transform(node, this))
				.flatMap(Optional::stream)
				.findAny()
				.orElse(node);
	}
}
