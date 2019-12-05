package com.meti.magma;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringTest extends MagmaTest {
    @Test
    void string() {
        assertEquals("String a0=\"Hello World!\";", compile("val x = \"Hello World!\";"));
    }
}
