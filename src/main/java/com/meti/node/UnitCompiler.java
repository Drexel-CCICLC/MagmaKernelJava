package com.meti.node;

import com.meti.compile.Compiler;
import com.meti.exception.ParseException;

import java.util.Collection;

public class UnitCompiler implements Compiler {
	private final Parser rootParser;
	private final Resolver rootResolver;

	public UnitCompiler(Parser root, Resolver rootResolver) {
		this.rootParser = root;
		this.rootResolver = rootResolver;
	}

	@Override
	public Node parseSingle(String value) {
		try {
			return rootParser.parseMultiple(value, this).stream().findFirst().orElseThrow(() -> failParse(value,
					null));
		} catch (Exception e) {
			throw failParse(value, e);
		}
	}

	@Override
	public Collection<Node> parseMultiple(String value) {
		try {
			return rootParser.parseMultiple(value, this);
		} catch (Exception e) {
			throw failParse(value, e);
		}
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
		return new ParseException("Failed to resolve name empty value:\n" + name, e);
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
		return new ParseException("Failed to resolve type empty value:\n" + value, e);
	}

	private RuntimeException failParse(String value, Throwable e) {
		return new ParseException("Failed to parse value:\n" + value, e);
	}
}
