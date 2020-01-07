package com.meti;

import java.util.List;
import java.util.Optional;

public class UnitCompiler implements Compiler {
	private static final String HEADER = "Failed to resolve type: ";
	private final List<Unit> units;

	public UnitCompiler(List<Unit> units) {
		this.units = units;
	}

	@Override
	public String compileAll(String input) {
		return compileOnly("val main = () => int : {" + input + ";return 0}");
	}

	@Override
	public String compileOnly(String input) {
		String trim = input.trim();
		return units.stream()
				.filter(unit -> unit.canCompile(trim))
				.map(unit -> unit.compile(trim, this))
				.findAny()
				.orElseThrow(() -> unknownType(trim));
	}

	@Override
	public Type resolveName(String value) {
		return units.stream()
				.map(unit -> unit.resolveName(value, this))
				.flatMap(Optional::stream)
				.findAny()
				.orElseThrow(() -> unknownType(value));
	}

	@Override
	public Type resolveValue(String value) {
		return units.stream()
				.map(unit -> unit.resolveValue(value, this))
				.flatMap(Optional::stream)
				.findAny()
				.orElseThrow(() -> unknownType(value));
	}

	private ParseException unknownType(String value) {
		return new ParseException(HEADER + value);
	}
}