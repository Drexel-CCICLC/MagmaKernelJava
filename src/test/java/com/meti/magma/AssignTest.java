package com.meti.magma;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssignTest extends MagmaTest {
    @Test
    void assign() {
        String content = "val x = 10;x = 20";
        String actual = compile(content);
        assertEquals("var a0=10;a0=20;", actual);
    }
}
