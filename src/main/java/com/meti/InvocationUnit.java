package com.meti;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InvocationUnit implements CompoundUnit {
	@Override
	public Optional<? extends Type> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.of(value)
				.filter(this::canCompile)
				.flatMap(invocation -> extractInvocation(invocation, compiler));
	}

	@Override
	public boolean canCompile(String value) {
		return value.trim().endsWith(")");
	}

	private Optional<Type> extractInvocation(String value, Compiler compiler) {
		String caller = parseCaller(value);
		Type type = compiler.resolveValue(caller);
		if (type instanceof StructType) return ((StructType) type).returnType();
		throw new CompileException(type.render() + "is not a structure.");
	}

	private String parseCaller(String value) {
		int parenthesis = invocationOpening(value);
		return value.substring(0, parenthesis);
	}

	private int invocationOpening(String value) {
		return value.indexOf('(');
	}

	@Override
	public String compile(String value, Compiler compiler) {
		String callerString = parseCaller(value);
		String argsString = parseArgs(value);
		Type callerType = compiler.resolveValue(callerString);
		if (callerType instanceof StructType) {
			StructType castedType = (StructType) callerType;
			String arguments = buildArguments(argsString, castedType);
			return buildInvocation(castedType, callerString, arguments);
		} else {
			throw new CompileException(callerType + " is not a structure.");
		}
	}

	private String parseArgs(String value) {
		int open = invocationOpening(value);
		int length = value.length();
		return value.substring(open + 1, length - 1);
	}

	private String buildArguments(String argsString, StructType callerType) {
		List<Type> parameters = callerType.parameters();
		List<String> args = splitArgs(argsString);
		checkArgsSize(parameters, args);
		return join();
	}

	private String buildInvocation(StructType callerType, String callerString, String arguments) {
		Optional<Type> optional = callerType.returnType();
		Type returnType = optional.orElseThrow();
		String end = (returnType instanceof VoidType) ? ";" : "";
		return callerString + "(" + arguments + ")" + end;
	}

	private List<String> splitArgs(String argsString) {
		return Arrays.stream(argsString.split(","))
				.filter(argString -> !argString.isBlank())
				.collect(Collectors.toList());
	}

	private void checkArgsSize(Collection<Type> parameters, Collection<String> args) {
		if (parameters.size() != args.size()) throw new CompileException("Invalid number of arguments.");
	}

	private String join() {
		throw new UnsupportedOperationException();
	}
}
