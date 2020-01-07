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
		declarations.push(name);
		Type type = compiler.resolveValue(value);
		String renderType = type.render();
		declarations.defineSibling(name, type);
		String compiledValue = compiler.compileOnly(value);
		declarations.pop();
		if (name.equals("main")) {
			return "";
		} else if (renderType.endsWith(")")) {
			callback.append(renderType)
					.append("=&")
					.append(name)
					.append("$;");
			return "";
		} else {
			return renderType + " " + name + "$=" + compiledValue + ";" +
					renderType + "* " + name + "=&" + name + "$;";
		}
	}

	@Override
	public Optional<Type> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.empty();
	}
}
