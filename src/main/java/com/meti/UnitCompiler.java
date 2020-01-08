package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UnitCompiler implements Compiler {
	private static final String HEADER = "Failed to resolve type: ";
	private final List<Resolver> resolvers = new ArrayList<>();
	private final List<Unit> units = new ArrayList<>();

	public UnitCompiler(List<Object> objects) {
		for (Object object : objects) {
			if (object instanceof Unit) units.add((Unit) object);
			if (object instanceof Resolver) resolvers.add((Resolver) object);
		}
	}

	@Override
	public String compileAll(String input) {
		return compileOnly("val main = () => int : {" + input + ";return 0}");
	}

	@Override
	public String compileOnly(String input) {
		String trim = input.trim();
		try {
			return units.stream()
					.filter(unit -> unit.canCompile(trim))
					.map(unit -> unit.compile(trim, this))
					.findAny()
					.orElseThrow(() -> unknownType(trim));
		} catch (Exception e) {
			throw new ParseException("Failed to compile: " + input, e);
		}
	}

	@Override
	public Type resolveName(String value) {
		return resolvers.stream()
				.map(resolver -> resolver.resolveName(value, this))
				.flatMap(Optional::stream)
				.findAny()
				.orElseThrow(() -> unknownType(value));
	}

	@Override
	public Type resolveValue(String value) {
		return resolvers.stream()
				.map(resolver -> resolver.resolveValue(value, this))
				.flatMap(Optional::stream)
				.findAny()
				.orElseThrow(() -> unknownType(value));
	}

	private RuntimeException unknownType(String value) {
		return new ParseException(HEADER + value);
	}
}