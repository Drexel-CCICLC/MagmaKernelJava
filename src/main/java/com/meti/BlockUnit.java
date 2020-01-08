package com.meti;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.meti.FunctionalPartitioner.using;

public class BlockUnit implements CompoundUnit {
	private final Depth depth = new SimpleDepth();

	@Override
	public boolean canCompile(String value) {
		String trim = value.trim();
		boolean starts = trim.startsWith("{");
		boolean ends = trim.endsWith("}");
		return starts && ends;
	}

	@Override
	public String compile(String value, Compiler compiler) {
		String content = cutContent(value);
		String joinedPartitions = joinPartitions(compiler, content);
		return "{" + joinedPartitions + "}";
	}

	private String cutContent(String value) {
		int length = value.length();
		return value.substring(1, length - 1);
	}

	private String joinPartitions(Compiler compiler, String content) {
		return partition(content)
				.map(compiler::compileOnly)
				.collect(Collectors.joining());
	}

	private Stream<String> partition(String content) {
		depth.level();
		return using(character -> character == ';', this::acceptCharacter)
				.partition(content);
	}

	private Optional<Character> acceptCharacter(Character character) {
		if (character == '{') depth.sink();
		if (character == '}') depth.surface();
		return Optional.of(character);
	}

	@Override
	public Optional<? extends Type> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.empty();
	}
}
