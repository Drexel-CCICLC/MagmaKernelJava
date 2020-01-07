package com.meti;

import java.util.Optional;

public class StructUnit implements Unit {
	private final Declarations declarations;

	public StructUnit(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public boolean canCompile(String value) {
		return value.contains(":");
	}

	@Override
	public String compile(String value, Compiler compiler) {
		String returnType = getReturnType(value, compiler);
		String last = declarations.stack().lastElement();
		String returnString = "(" + paramString(value, compiler) + ")";
		return returnType + " " + last + "$" + returnString + "{}";
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
				String paramType = compiler.resolveName(type);
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
				String paramType = compiler.resolveName(param.substring(0, space));
				builder.append(paramType)
						.append("*");
			}
		}
		return builder.toString();
	}

	@Override
	public Optional<String> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<String> resolveValue(String value, Compiler compiler) {
		if (canCompile(value)) {
			String returnType = getReturnType(value, compiler);
			String currentName = declarations.stack().lastElement();
			String paramString = paramTypeString(value, compiler);
			return Optional.of(returnType + "(*" + currentName + ")(" + paramString + ")");
		}
		return Optional.empty();
	}

	private String getReturnType(String value, Compiler compiler) {
		int index = value.indexOf(':');
		String header = value.substring(0, index);
		int returnIndex = header.indexOf("=>");
		return compiler.resolveName(header.substring(returnIndex + 2));
	}
}
