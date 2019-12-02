package com.meti.lex;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenizerLexerTest {

    @Test
    void parse() {
        Lexer lexer = new TokenizerLexer(singleton(this::tokenize));
        List<Token<?>> tokens = lexer.parse("25963");
        assertTrue(tokens.stream()
                .allMatch(token -> token.type().equals(TokenType.CONTENT)));
        assertIterableEquals(List.of(true, false, false, true, false),
                tokens.stream()
                        .map(Token::value)
                        .collect(Collectors.toList()));
    }

    private Optional<Token<?>> tokenize(LexerState state) {
        try {
            String value = state.compute();
            int number = Integer.parseInt(value);
            boolean isEven = number % 2 == 0;
            return Optional.of(new TokenImpl<>(TokenType.CONTENT, isEven));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}