package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CastTest extends CompileTest {
    @Test
    void test() {
        String result = compileOnly("<string>10");
        assertEquals("(char*)10", result);
    }
}
