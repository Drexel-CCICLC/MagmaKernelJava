package com.meti.unit;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Type;

import java.util.Arrays;
import java.util.Optional;

public class CompoundUnit implements Unit {
	private final Unit[] children;

	protected CompoundUnit(Unit... children) {
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
