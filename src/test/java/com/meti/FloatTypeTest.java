package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FloatTypeTest {

    @Test
    void render() {
        Type type = new FloatType();
        assertEquals("float", type.render());
    }
}