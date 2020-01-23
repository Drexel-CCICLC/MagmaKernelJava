package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntTypeTest {

    @Test
    void render() {
        IntType type = new IntType();
        assertEquals("int", type.render());
    }
}