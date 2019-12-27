package com.meti.unit.value;

import com.meti.Compiler;
import com.meti.exception.TypeClashException;
import com.meti.type.Type;
import com.meti.type.TypeStack;
import com.meti.unit.Data;
import com.meti.unit.Unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class InvocationUnit implements Unit {
	private final TypeStack stack;

	public InvocationUnit(Data data) {
		this.stack = data.getTypeStack();
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		if (trimmedInput.endsWith(")")) {
			int open = trimmedInput.indexOf('(');
			int close = trimmedInput.lastIndexOf(')');
			String caller = trimmedInput.substring(0, open);
			String content = trimmedInput.substring(open + 1, close);
			Collection<String> partitions = new ArrayList<>();
			StringBuilder builder = new StringBuilder();
			int depth = 0;
			for (char c : content.toCharArray()) {
				if (c == ',' && depth == 0) {
					partitions.add(builder.toString());
					builder = new StringBuilder();
				} else {
					if (c == '(') {
						depth++;
					} else if (c == ')') {
						depth--;
					}
					builder.append(c);
				}
			}
			partitions.add(builder.toString());
			String contentString = partitions.stream()
					.filter(value -> !value.isBlank())
					.map(compiler::compile)
					.collect(Collectors.joining(","));
			String callString = compiler.compile(caller);
			Type callerType = stack.poll();
			Collection<Type> children = callerType.children();
			for (Type child : children) {
				Type other = stack.poll();
				if (!child.equals(other)) {
					throw new TypeClashException("Parameter type should be " + child + " but was " + other + ".");
				}
			}
			stack.add(callerType.returnType().orElseThrow());
			return Optional.of(callString + "(" + contentString + ")");
		}
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolve(String input, Compiler compiler) {
		return Optional.empty();
	}
}
