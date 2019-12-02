package com.meti;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
        LexerState state = new StringLexerState("test");
        assertEquals("t", state.compute());
    }

    @Test
    void hasMoreTokens() {
        LexerState state = new StringLexerState("t");
        Optional<Token<?>> expected = Optional.of(Mockito.mock(Token.class));
        Optional<Token<?>> actual = state.next(singleton(lexerState -> expected));
        assertEquals(expected, actual);
        assertFalse(state.hasMoreTokens());
    }
}