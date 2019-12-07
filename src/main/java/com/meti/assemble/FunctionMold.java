package com.meti.assemble;

import com.meti.lex.Token;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.meti.lex.TokenType.CONTENT;
import static com.meti.lex.TokenType.PARENTHESIS;

public class FunctionMold extends BucketMold {
	private final BucketManager<Token<?>> manager = new ListBucketManager<>(
			by(type(PARENTHESIS).and(valueEquals(true))),
			by(type(PARENTHESIS).and(valueEquals(false).negate())),
			by(type(PARENTHESIS).and(valueEquals(false))),
			by(type(CONTENT).and(valueEquals("=>"))),
			by(type(CONTENT).and(single())),
			by(type(CONTENT).and(valueEquals(":"))),
			by(any())
	);

	@Override
	public void pour(Token<?> token) {
		manager.put(token);
	}

	@Override
	public Optional<Node> set(Assembler assembler) {
		if (manager.allPresent(0, 2, 3, 4, 6)) {
			Stream<Stream<Token<?>>> parameters = manager.split(1, token -> token.type().equals(CONTENT) &&
					token.value().equals(","));
			Map<Object, Object> parameterMap =
					parameters.map(tokenStream -> tokenStream.collect(Collectors.toList()))
							.filter(tokens -> !tokens.isEmpty())
							.collect(Collectors.toMap(tokens -> tokens.get(0).value(),
									tokens -> tokens.get(1).value()));
			String value = manager.atSingle(4).castedValue();
			Node content = assembler.assembleSingle(manager.at(6)).orElseThrow();
			return Optional.of(new FunctionNode(parameterMap, value, content));
		}
		return Optional.empty();
	}
}
