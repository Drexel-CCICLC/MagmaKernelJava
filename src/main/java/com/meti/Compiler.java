package com.meti;

import com.meti.exception.ParseException;

public class Compiler {
	private final Parser rootParser;
	private final Resolver rootResolver;

	public Compiler(Parser rootParser, Resolver rootResolver) {
		this.rootParser = rootParser;
		this.rootResolver = rootResolver;
	}

	public Node parse(String value) throws ParseException {
		return rootParser.parse(value, this).orElseThrow();
	}

	public Type resolveName(String name) {
		return rootResolver.resolveName(name, this).orElseThrow();
	}

	public Type resolveValue(String value) {
		return rootResolver.resolveValue(value, this).orElseThrow();
	}
}