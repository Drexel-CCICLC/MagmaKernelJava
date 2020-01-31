package com.meti.core;

import com.meti.*;
import com.meti.Compiler;
import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.Type;

public class UnitCompiler implements Compiler {
	private final Parser rootParser;
	private final Resolver rootResolver;

	public UnitCompiler(Parser rootParser, Resolver rootResolver) {
		this.rootParser = rootParser;
		this.rootResolver = rootResolver;
	}

	@Override
	public Node parse(String value) {
		try {
			return rootParser.parse(value, this).orElseThrow(() -> failParse(value, null));
		} catch (Exception e) {
			throw failParse(value, e);
		}
	}

	private RuntimeException failParse(String value, Throwable e) {
		return new ParseException("Failed to parse value:\n" + value, e);
	}

	@Override
	public Type resolveName(String name) {
		try {
			return rootResolver.resolveName(name, this).orElseThrow(() -> failName(name, null));
		} catch (Exception e) {
			throw failName(name, e);
		}
	}

	private RuntimeException failName(String name, Throwable e) {
		return new ParseException("Failed to resolve name of value:\n" + name, e);
	}

	@Override
	public Type resolveValue(String value) {
		try {
			return rootResolver.resolveValue(value, this).orElseThrow(() -> failValue(value, null));
		} catch (Exception e) {
			throw failValue(value, e);
		}
	}

	private RuntimeException failValue(String value, Throwable e) {
		return new ParseException("Failed to resolve type of value:\n" + value, e);
	}
}
