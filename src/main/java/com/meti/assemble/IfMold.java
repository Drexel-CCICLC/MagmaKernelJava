package com.meti.assemble;

import com.meti.lex.Token;

import java.util.Optional;

import static com.meti.lex.Keyword.ELSE;
import static com.meti.lex.Keyword.IF;
import static com.meti.lex.TokenType.KEYWORD;
import static com.meti.lex.TokenType.PARENTHESIS;

public class IfMold extends BucketMold {
	private final BucketManager<Token<?>> manager = new ListBucketManager<>(
			by(type(KEYWORD).and(valueEquals(IF)).and(single())),
			by(type(PARENTHESIS).and(valueEquals(true).and(single()))),
			by(type(PARENTHESIS).and(valueEquals(false)).negate()),
			by(type(PARENTHESIS).and(valueEquals(false))),
			by(type(KEYWORD).and(valueEquals(ELSE)).negate()),
			by(type(KEYWORD).and(valueEquals(ELSE))),
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
			Node ifBlock = assembler.assemble(manager.at(4)).findAny().orElseThrow();
			Node elseBlock = assembler.assemble(manager.at(6)).findAny().orElseThrow();
			return Optional.of(new IfNode(condition, ifBlock, elseBlock));
		}
		return Optional.empty();
	}
}
