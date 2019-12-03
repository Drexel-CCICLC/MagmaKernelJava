package com.meti.lex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TokenizerLexer implements Lexer {
    private final Set<? extends Tokenizer> tokenizers;

    public TokenizerLexer(Set<? extends Tokenizer> tokenizers) {
        this.tokenizers = tokenizers;
    }

    @Override
    public List<Token<?>> parse(String value) {
        LexerState state = new StringLexerState(value);
        List<Token<?>> tokens = new ArrayList<>();
        while (state.hasMoreCharacters()) {
            state.next(tokenizers).ifPresent(tokens::add);
        }
        String compute = state.compute();
        if (!compute.isEmpty()) {
            throw new IllegalArgumentException("Failed to tokenize: \"" + compute + "\"");
        }
        return tokens;
    }
}