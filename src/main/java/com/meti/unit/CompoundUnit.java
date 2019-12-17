package com.meti.unit;

import com.meti.Compiler;

import java.util.List;
import java.util.Optional;

public class CompoundUnit implements Unit {
	final List<Unit> units;

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
}