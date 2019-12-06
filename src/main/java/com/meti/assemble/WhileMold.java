package com.meti.assemble;

import com.meti.lex.Token;

import java.util.Optional;

import static com.meti.lex.Keyword.*;
import static com.meti.lex.TokenType.KEYWORD;
import static com.meti.lex.TokenType.PARENTHESIS;

public class WhileMold extends SimpleMold {
	private final BucketManager<Token<?>> manager = new ListBucketManager<>(
			by(type(KEYWORD).and(valueEquals(WHILE)).and(single())),
			by(type(PARENTHESIS).and(valueEquals(true).and(single()))),
			by(type(PARENTHESIS).and(valueEquals(false)).negate()),
			by(type(PARENTHESIS).and(valueEquals(false))),
			by(any())
	);

	@Override
	public void pour(Token<?> token) {
		manager.put(token);
	}

	@Override
	public Optional<Node> set(Assembler assembler) {
		if (manager.allPresent()) {
			Node condition = assembler.assemble(manager.at(2)).findAny().orElseThrow();
			Node content = assembler.assemble(manager.at(4)).findAny().orElseThrow();
			return Optional.of(new WhileNode(condition, content));
		}
		return Optional.empty();
	}
}
