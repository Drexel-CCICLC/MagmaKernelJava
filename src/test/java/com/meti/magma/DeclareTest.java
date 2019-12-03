package com.meti.magma;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeclareTest extends MagmaTest {
    @Test
    void test(){
        String result = compile("val x = 10;");
        assertEquals("var a0=10;", result);
    }
}
