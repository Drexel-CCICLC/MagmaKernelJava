package com.meti.magma;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeclareTest extends MagmaTest {
    @Test
    void test() {
        String result = compile("val x = 10;");
        assertEquals("int a0=10;", result);
    }
}
