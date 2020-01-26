package com.meti;

import com.meti.primitive.VoidType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.IntStream;

public class StructResolver implements Resolver {
	private int implStart = 0;
	private int paramStart = 0;
	private int returnStart = 0;

	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		paramStart = content.indexOf('(');
		returnStart = content.indexOf("=>");
		implStart = content.indexOf(':');
		if (allEmpty(paramStart, returnStart)) {
			return Optional.empty();
		} else {
			Collection<Type> parameters = parseParameters(content, compiler);
			Type returnType = parseReturnType(content, compiler);
			return Optional.of(new FunctionType(parameters, returnType));
		}
	}

	private boolean allEmpty(int paramIndex, int returnIndex) {
		return IntStream.of(paramIndex, returnIndex)
				.allMatch(value -> -1 == value);
	}

	private Collection<Type> parseParameters(String content, Compiler compiler) {
		Collection<Type> parameters = new ArrayList<>();
		if (-1 != paramStart) {
			int paramEnd = endOf(content, returnStart, implStart);
			String paramsString = content.substring(paramStart, paramEnd).trim();
			if (paramsString.startsWith("(") && paramsString.endsWith(")")) {
				Arrays.stream(paramsString.substring(1, paramsString.length() - 1).split(","))
						.map(s -> parseParam(s, compiler))
						.forEach(parameters::add);
			}
		}
		return parameters;
	}

	private Type parseReturnType(String content, Compiler compiler) {
		Type returnType = VoidType.INSTANCE;
		if (-1 != returnStart) {
			int returnEnd = endOf(content, implStart);
			String returnString = content.substring(returnStart, returnEnd).trim();
			if (returnString.startsWith("=>")) {
				returnType = compiler.resolveName(returnString.substring(2));
			}
		}
		return returnType;
	}

	private int endOf(String content, int... indices) {
		return IntStream.concat(IntStream.of(indices), IntStream.of(content.length()))
				.filter(value -> -1 != value)
				.min().orElseThrow();
	}

	private Type parseParam(String s, Compiler compiler) {
		int lastSpace = s.lastIndexOf(' ');
		String type = s.substring(0, lastSpace);
		return compiler.resolveName(type);
	}
}
