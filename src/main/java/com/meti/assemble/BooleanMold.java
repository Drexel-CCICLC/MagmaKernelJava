package com.meti.assemble;

import com.meti.lex.Token;
import com.meti.lex.TokenType;

import java.util.Optional;
import java.util.function.Function;

class BooleanMold implements Mold {
	private Token<?> value;

	@Override
	public void pour(Token<?> token) {
		boolean isBoolean = token.value().equals("true") ||
				token.value().equals("false");
		if (token.type() == TokenType.CONTENT && isBoolean) {
            this.value = token;
		}
	}

	@Override
	public Optional<Node> set(Assembler assembler) {
		return Optional.ofNullable(value)
				.map((Function<Token<?>, String>) Token::castedValue)
                .map(Boolean::parseBoolean)
				.map(BooleanNode::new);
	}
}

