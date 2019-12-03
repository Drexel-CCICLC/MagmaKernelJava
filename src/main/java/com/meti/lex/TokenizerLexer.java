package com.meti.lex;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TokenizerLexer implements Lexer {
    private final Set<? extends Tokenizer> tokenizers;

    public TokenizerLexer(Set<? extends Tokenizer> tokenizers) {
        this.tokenizers = tokenizers;
    }

    @Override
    public List<Token<?>> parse(String value) {
        LexerState lexerState = new StringLexerState(value);
        var list = new ArrayList<Token<?>>();
        do {
            lexerState.skipWhitespace();
            var optional = compute(lexerState);
            if (optional.isPresent()) {
                lexerState.advance();
                list.add(optional.get());
            } else {
                lexerState.extend();
            }
        } while (lexerState.hasMoreToScan());
        return list;
    }

    private Optional<? extends Token<?>> compute(LexerState state) {
        return tokenizers.stream()
                .map(tokenizer -> tokenizer.apply(state))
                .flatMap(Optional::stream)
                .findAny();
    }
}