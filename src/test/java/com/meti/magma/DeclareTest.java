package com.meti.magma;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeclareTest extends MagmaTest {
    @Test
    void immutable() {
        assertEquals("int a0=10;", compile("val x = 10;"));
    }

    @Test
    void mutable(){
        assertEquals("int a0=20;", compile("var y = 20;"));
    }
}
