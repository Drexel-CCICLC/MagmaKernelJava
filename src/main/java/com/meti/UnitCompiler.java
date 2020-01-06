package com.meti;

import java.util.List;

public class UnitCompiler implements Compiler {
	private final List<Unit> units;

	public UnitCompiler(List<Unit> units) {
		this.units = units;
	}

	@Override
	public String compileAll(String input) {
		String callback = compileOnly(input);
		return "int main(){" + callback + "return 0;}";
	}

	@Override
	public String compileOnly(String input) {
		String trim = input.trim();
		return units.stream()
				.filter(unit -> unit.canCompile(trim))
				.map(unit -> unit.compile(trim, this))
				.findAny()
				.orElseThrow(() -> new ParseException("Failed to parse: " + trim));
	}
}