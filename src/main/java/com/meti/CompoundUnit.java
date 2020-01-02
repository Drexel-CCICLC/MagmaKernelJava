package com.meti;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class CompoundUnit implements Unit {
	private final Collection<Unit> units;

	public CompoundUnit(Unit... units) {
		this(Arrays.asList(units));
	}

	public CompoundUnit(Collection<Unit> units) {
		this.units = units;
	}

	@Override
	public Optional<Node> assemble(String value, Assembler assembler) {
		return units.stream()
				.map(unit -> assembleHelper(unit, value, assembler))
				.flatMap(Optional::stream)
				.findAny();
	}

	private Optional<Node> assembleHelper(Unit unit, String value, Assembler assembler) {
		try {
			return unit.assemble(value, assembler);
		} catch (Exception e) {
			throw new ParseException(value, e);
		}
	}

	@Override
	public Optional<Type> resolveName(String name, Assembler assembler) {
		return units.stream()
				.map(unit -> unit.resolveName(name, assembler))
				.flatMap(Optional::stream)
				.findAny();
	}

	@Override
	public Optional<Type> resolveValue(String value, Assembler assembler) {
		return units.stream()
				.map(unit -> unit.resolveValue(value, assembler))
				.flatMap(Optional::stream)
				.findAny();
	}
}
