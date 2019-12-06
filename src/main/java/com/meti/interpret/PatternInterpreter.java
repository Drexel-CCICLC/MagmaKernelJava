package com.meti.interpret;

import com.meti.assemble.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

class PatternInterpreter implements Interpreter {
	private final Set<? extends Pattern> patterns;
	private final Set<? extends Resolver> resolvers;
	private final List<Statement> statements = new ArrayList<>();

	PatternInterpreter(Set<? extends Pattern> patterns, Set<? extends Resolver> resolvers) {
		this.resolvers = resolvers;
		this.patterns = patterns;
	}

	@Override
	public Type resolve(Node node) {
		return resolvers.stream()
                .map(resolver -> resolver.resolve(node, this))
				.flatMap(Optional::stream)
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("Could not resolve type of " + node));
	}

	@Override
	public Type resolve(String name) {
		return resolvers.stream()
				.map(resolver -> resolver.resolve(name))
				.flatMap(Optional::stream)
				.findAny()
				.orElseThrow();
	}

	@Override
	public Optional<Statement> interpret(Node node) {
		return patterns.stream()
				.map(pattern -> pattern.match(node, this))
				.flatMap(Optional::stream)
				.findAny();
	}

	@Override
	public void interpret(Stream<? extends Node> nodes) {
		nodes.map(this::interpret)
				.flatMap(Optional::stream)
				.forEach(statements::add);
	}

	@Override
	public List<Statement> collect() {
		return statements;
	}
}
