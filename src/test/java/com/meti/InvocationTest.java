package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvocationTest extends CompileTest {
    @Test
    void test() {
        String result = compileOnly("{val empty = () => void :{}; empty();}");
        assertEquals("void empty(){}{empty();}", result);
    }

    @Test
    void withParam() {
        String result = compileOnly("{val some = (int x) => void : {}; val y = 20; some(y);}");
        assertEquals("void some(int* x){}{int y=20;some(&y);}", result);
    }

    @Test
    void tempValue() {
        String result = compileOnly("{val some = (int x) => void : {}; some(20);}");
		assertEquals("void some(int* x){}{int a0=20;some(&a0);}", result);
    }
}
