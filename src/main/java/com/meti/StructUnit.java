package com.meti;

import java.util.Optional;

public class StructUnit implements Unit {
	private final StringBuilder callback;
	private final Declarations declarations;

	public StructUnit(StringBuilder callback, Declarations declarations) {
		this.callback = callback;
		this.declarations = declarations;
	}

	@Override
	public boolean canCompile(String value) {
		return value.contains(":");
	}

	@Override
	public String compile(String value, Compiler compiler) {
		String returnType = returnType(value, compiler).render();
		String name = declarations.stack().lastElement();
		String paramString = "(" + paramString(value, compiler) + ")";
		int index = value.indexOf(':');
		String content = value.substring(index + 1);
		String compiledContent = compiler.compileOnly(content);
		String formattedName = (name.equals("main")) ? "main" : name + "$";
		String result = returnType + " " + formattedName + paramString + compiledContent;
		callback.append(result);
		return "";
	}

	private String paramString(String value, Compiler compiler) {
		int start = value.indexOf('(');
		int end = value.indexOf(')');
		String[] params = value.substring(start + 1, end).split(",");
		StringBuilder builder = new StringBuilder();
		for (String param : params) {
			if (!param.isBlank()) {
				int last = param.lastIndexOf(' ');
				String type = param.substring(0, last);
				String name = param.substring(last + 1);
				String paramType = compiler.resolveName(type).render();
				builder.append(paramType)
						.append("* ")
						.append(name);
			}
		}
		return builder.toString();
	}

	private String paramTypeString(String value, Compiler compiler) {
		int start = value.indexOf('(');
		int end = value.indexOf(')');
		String[] params = value.substring(start + 1, end).split(",");
		StringBuilder builder = new StringBuilder();
		for (String param : params) {
			if (!param.isBlank()) {
				int space = param.lastIndexOf(' ');
				String paramType = compiler.resolveName(param.substring(0, space)).render();
				builder.append(paramType)
						.append("*");
			}
		}
		return builder.toString();
	}

	@Override
	public Optional<Type> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		if (canCompile(value)) {
			Type returnType = returnType(value, compiler);
			String currentName = declarations.stack().lastElement();
			String paramString = paramTypeString(value, compiler);
			return Optional.of(returnType.render() + "(*" + currentName + ")(" + paramString + ")")
					.map(s -> new Type(s, returnType));
		}
		return Optional.empty();
	}

	private Type returnType(String value, Compiler compiler) {
		int index = value.indexOf(':');
		String header = value.substring(0, index);
		int returnIndex = header.indexOf("=>");
		return compiler.resolveName(header.substring(returnIndex + 2));
	}
}
