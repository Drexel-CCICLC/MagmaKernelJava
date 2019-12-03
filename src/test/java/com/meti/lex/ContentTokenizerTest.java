package com.meti.lex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ContentTokenizerTest {

    @Test
    void validate() {
        var tokenizer = new ContentTokenizer();
        assertTrue(tokenizer.validate(new StringLexerState("x")));
    }
}