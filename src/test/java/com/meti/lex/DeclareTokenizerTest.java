package com.meti.lex;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeclareTokenizerTest {

    @Test
    void apply() {
        Tokenizer tokenizer = new DeclareTokenizer();
        LexerState state = new StringLexerState("val x")
                .extend()
                .extend();
        Optional<? extends Token<?>> optional = tokenizer.apply(state);
        assertTrue(optional.isPresent());
        Token<?> token = optional.get();
        assertEquals(TokenType.DECLARE, token.type());
        assertEquals(false, token.value());
    }
}