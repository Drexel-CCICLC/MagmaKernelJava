package com.meti.assemble;

import com.meti.lex.Token;
import com.meti.lex.TokenType;
import com.meti.util.PredicateStreamSplitter;
import com.meti.util.StreamSplitter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MoldAssembler implements Assembler {
	private final List<? extends NodeMoldFactory> moldFactories;

	MoldAssembler(List<? extends NodeMoldFactory> moldFactories) {
		this.moldFactories = moldFactories;
	}

	@Override
	public Stream<Node> assemble(Stream<? extends Token<?>> tokens) {
		StreamSplitter<Token<?>> splitter =
				new PredicateStreamSplitter<>(((Predicate<Token<?>>) token -> token.type().equals(TokenType.END))
						.and(token -> token.value().equals(0)));
		return splitter.split(tokens)
				.map(this::assembleSingle)
				.flatMap(Optional::stream);
	}

	private Optional<Node> assembleSingle(Stream<? extends Token<?>> tokenStream) {
		List<Token<?>> tokens = tokenStream.collect(Collectors.toList());
		return moldFactories.stream()
				.map((Function<NodeMoldFactory, NodeMold>) NodeMoldFactory::create)
				.peek(nodeMold -> nodeMold.pourAll(tokens.stream()))
				.map(mold -> mold.set(this))
				.flatMap(Optional::stream)
				.findFirst();
	}
}
