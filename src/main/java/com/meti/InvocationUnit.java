package com.meti;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InvocationUnit implements Unit {
	@Override
	public boolean canCompile(String value) {
		return value.endsWith(")");
	}

	@Override
	public String compile(String value, Compiler compiler) {
		int open = value.indexOf('(');
		String name = value.substring(0, open);
		String parameterString = value.substring(open + 1, value.length() - 1);
		Type struct = compiler.resolveValue(name);
		List<Type> parameters = struct.parameters();
		List<String> args = Arrays.stream(parameterString.split(","))
				.filter(string -> !string.isBlank())
				.collect(Collectors.toList());
		if (parameters.size() != args.size()) throw new CompileException("Invalid number of arguments.");
		String params = join(parameters, args, compiler);
		Type returnType = struct.returnType().orElseThrow();
		String end = (returnType.render().equals("void")) ? ";" : "";
		return name + "(" + params + ")" + end;
	}

	private String join(List<Type> parameters, List<String> args, Compiler compiler) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < parameters.size(); i++) {
			Type expectedType = parameters.get(i);
			String paramValue = args.get(i);
			Type actualType = compiler.resolveValue(paramValue);
			if (expectedType.equals(actualType)) {
				builder.append(compiler.compileOnly(paramValue));
			} else {
				Optional<Type> child = expectedType.child();
				if (child.isPresent() && child.get().equals(actualType)) {
					builder.append("*").append(compiler.compileOnly(paramValue));
				} else {
					throw new CompileException("Type mismatch.");
				}
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
		return Optional.empty();
	}
}
