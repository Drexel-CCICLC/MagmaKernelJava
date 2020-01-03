package com.meti;

import java.util.Arrays;
import java.util.Optional;

class CompoundUnit implements Unit {
	private final Unit[] children;

	CompoundUnit(Unit... children) {
		this.children = children;
	}

	@Override
	public Optional<Node> compile(String value, Compiler compiler) {
		return Arrays.stream(children)
				.map(unit -> unit.compile(value, compiler))
				.flatMap(Optional::stream)
				.findFirst();
	}

	@Override
	public Optional<Type> resolve(String value, Compiler compiler) {
		return Arrays.stream(children)
				.map(unit -> unit.resolve(value, compiler))
				.flatMap(Optional::stream)
				.findFirst();
	}
}
