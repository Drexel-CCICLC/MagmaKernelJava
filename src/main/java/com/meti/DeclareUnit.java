package com.meti;

import java.util.Optional;

public class DeclareUnit implements Unit {
	private final StringBuilder callback;
	private final Declarations declarations;

	public DeclareUnit(StringBuilder callback, Declarations declarations) {
		this.callback = callback;
		this.declarations = declarations;
	}

	@Override
	public boolean canCompile(String value) {
		return value.startsWith("val");
	}

	@Override
	public String compile(String trim, Compiler compiler) {
		int index = trim.indexOf('=');
		String keys = trim.substring(0, index).trim();
		int lastSpace = keys.lastIndexOf(' ');
		String name = keys.substring(lastSpace + 1);
		String value = trim.substring(index + 1);
		declarations.define(name, value);
		declarations.push(name);
		String type = compiler.resolveValue(value);
		String compiledValue = compiler.compileOnly(value);
		declarations.pop();
		if (name.equals("main")) {
			return "";
		} else if (type.endsWith(")")) {
			callback.append(type)
					.append("=&")
					.append(name)
					.append("$;");
			return "";
		} else {
			return type + " " + name + "$=" + compiledValue + ";" +
					type + "* " + name + "=&" + name + "$;";
		}
	}

	@Override
	public Optional<String> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<String> resolveValue(String value, Compiler compiler) {
		return Optional.empty();
	}
}
