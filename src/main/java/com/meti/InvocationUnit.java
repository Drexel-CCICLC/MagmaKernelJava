package com.meti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InvocationUnit implements Unit {
	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	private int counter = -1;

	private final Declarations declarations;

	public InvocationUnit(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public boolean canCompile(String value) {
		return value.trim().endsWith(")");
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
		List<String> items = new ArrayList<>();
		for (int i = 0; i < parameters.size(); i++) {
			Type expectedType = parameters.get(i);
			String paramValue = args.get(i);
			Type actualType = compiler.resolveValue(paramValue);

			if (expectedType.render().equals(".")) {
				Type type = expectedType.child().orElseThrow();
				Optional<Type> childOptional = type.child();

				if (childOptional.isPresent()) {
					Type child = childOptional.get();
					if (child.equals(actualType) || child.render().equals("void*")) {
						items.add("*" + compiler.compileOnly(paramValue));
					} else {
						throw new CompileException("Type mismatch.");
					}
				} else {
					throw new CompileException("Type mismatch.");
				}
			} else if (canAssign(expectedType, actualType)) {
				String name = actualType.render();
				String address;
				String compiledValue = compiler.compileOnly(paramValue);
				if (isPrimitive(paramValue)) {
					counter++;
					String tempName = ALPHABET.charAt(counter % ALPHABET.length()) + String.valueOf(counter);
					declarations.parent().callback().append(name)
							.append(" ")
							.append(tempName)
							.append("=")
							.append(compiledValue)
							.append(";");
					address = tempName;
				} else {
					address = compiledValue;
				}
				items.add("&" + address);
			} else {
				Optional<Type> childOptional = expectedType.child();
				if (childOptional.isPresent()) {
					Type child = childOptional.get();
					if (child.equals(AnyType.INSTANCE) || child.equals(actualType)) {
						items.add(compiler.compileOnly(paramValue));
					} else {
						throw new CompileException("Type mismatch.");
					}
				} else {
					throw new CompileException("Type mismatch.");
				}
			}
		}
		return String.join(",", items);
	}

	private boolean canAssign(Type expectedType, Type actualType) {
		if (expectedType == AnyType.INSTANCE && actualType.isPointer()) {
			return true;
		}
		return expectedType.equals(actualType);
	}

	private boolean isPrimitive(String value) {
		boolean containsLetter = false;
		for (char c : value.toCharArray()) {
			if (Character.isLetter(c)) {
				containsLetter = true;
			}
		}
		if (containsLetter) {
			boolean isString = value.startsWith("\"") && value.endsWith("\"");
			boolean isChar = value.startsWith("'") && value.endsWith("'");
			return isString || isChar;
		} else {
			return true;
		}
	}

	@Override
	public Optional<? extends Type> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		if (canCompile(value)) {
			int parenthesis = value.indexOf('(');
			String caller = value.substring(0, parenthesis);
			Type type = compiler.resolveValue(caller);
			return type.returnType();
		} else {
			return Optional.empty();
		}
	}
}
