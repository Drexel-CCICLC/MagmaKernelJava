package com.meti.magma;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringTest extends MagmaCompiler {
    @Test
    void string() {
        assertEquals("var a0=\"Hello World!\";", compile("var x = \"Hello World!\";"));
    }
}
