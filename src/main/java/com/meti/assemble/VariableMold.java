package com.meti.assemble;

import com.meti.lex.Token;
import com.meti.lex.TokenType;

import java.util.Optional;
import java.util.function.Function;

public class VariableMold implements Mold {
	private Token<?> variable = null;

	@Override
	public void pour(Token<?> token) {
		if (token.type() == TokenType.CONTENT) {
			variable = token;
		}
	}

	@Override
	public Optional<Node> set(Assembler assembler) {
		return Optional.ofNullable(variable)
				.map((Function<Token<?>, String>) Token::castedValue)
				.map(VariableNode::new);
	}
}
