package com.meti;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class UnitAssembler implements Assembler {
	private final Collection<Unit> units;

	public UnitAssembler(Unit... units) {
		this(Arrays.asList(units));
	}

	public UnitAssembler(Collection<Unit> units) {
		this.units = units;
	}

	@Override
	public Collection<Node> assembleMultiple(String value) {
		return BracketPartitioner.create()
				.partition(value)
				.stream()
				.map(this::assembleSingle)
				.collect(Collectors.toList());
	}

	@Override
	public Node assembleSingle(String value) {
		return units.stream()
				.map(unit -> assembleHelper(value, unit))
				.flatMap(Optional::stream)
				.findAny()
				.orElseThrow(() -> new ParseException(value));
	}

	@Override
	public Type resolveName(String name) {
		return units.stream()
				.map(unit -> unit.resolveName(name, this))
				.flatMap(Optional::stream)
				.findAny()
				.orElseThrow();
	}

	@Override
	public Type resolveValue(String value) {
		return units.stream()
				.map(unit -> unit.resolveValue(value, this))
				.flatMap(Optional::stream)
				.findAny()
				.orElseThrow();
	}

	private Optional<Node> assembleHelper(String value, Unit unit) {
		try {
			return unit.assemble(value, this);
		} catch (Exception e) {
			throw new ParseException(value, e);
		}
	}
}
