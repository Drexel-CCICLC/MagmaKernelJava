package com.meti.lex;

import java.util.Optional;
import java.util.Set;

class ContentTokenizer extends FunctionalTokenizer {
    private final Set<Character> validTrails = Set.of(
            ' ',
            ';',
            ')',
            '{'
    );

    @Override
    protected Token<?> buildToken(LexerState lexerState) {
        return new TokenImpl<>(TokenType.CONTENT, lexerState.compute());
    }

    @Override
    protected boolean validate(LexerState lexerState) {
        String value = lexerState.compute();
        if (value.isBlank() || value.startsWith("\"")) return false;
        Optional<Character> trail = lexerState.trailing();
        if (trail.isEmpty()) return true;
        return validTrails.stream().anyMatch(character -> character == trail.get());
    }
}
