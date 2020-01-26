package com.meti;

import com.meti.exception.ParseException;

public class UnitCompiler implements Compiler {
	private final Parser rootParser;
	private final Resolver rootResolver;

	UnitCompiler(Parser rootParser, Resolver rootResolver) {
		this.rootParser = rootParser;
		this.rootResolver = rootResolver;
	}

	@Override
	public Node parse(String value) throws ParseException {
		return rootParser.parse(value, this).orElseThrow(() -> new ParseException("Failed to parse: " + value));
	}

	@Override
	public Type resolveName(String name) {
		return rootResolver.resolveName(name, this).orElseThrow();
	}

	@Override
	public Type resolveValue(String value) {
		return rootResolver.resolveValue(value, this).orElseThrow();
	}
}