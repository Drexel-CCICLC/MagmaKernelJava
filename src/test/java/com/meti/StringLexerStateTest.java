package com.meti;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StringLexerStateTest {

    @Test
    void next() {
        LexerState state = new StringLexerState("test");
        Optional<Token<?>> expected = Optional.of(Mockito.mock(Token.class));
        Optional<Token<?>> actual = state.next(singleton(lexerState -> expected));
        assertEquals(expected, actual);
        assertEquals("e", state.compute());
    }

    @Test
    void compute() {
    }

    @Test
    void hasMoreTokens() {
    }
}