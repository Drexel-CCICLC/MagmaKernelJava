package com.meti.unit;

import com.meti.Compiler;
import com.meti.type.Type;

import java.util.List;
import java.util.Optional;

public class CompoundUnit implements Unit {
	private final List<Unit> units;

	public CompoundUnit(Unit... units) {
		this(List.of(units));
	}

	public CompoundUnit(List<Unit> units) {
		this.units = units;
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		return units.stream()
				.map(unit -> unit.parse(input, compiler))
				.flatMap(Optional::stream)
				.findAny();
	}

	@Override
	public Optional<Type> resolve(String input, Compiler compiler) {
		return units.stream()
				.map(unit -> unit.resolve(input, compiler))
				.flatMap(Optional::stream)
				.findAny();
	}
}