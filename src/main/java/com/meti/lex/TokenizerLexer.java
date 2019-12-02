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
        while (state.hasMoreTokens()) {
            state.next(tokenizers).ifPresent(tokens::add);
        }
        return tokens;
    }
}