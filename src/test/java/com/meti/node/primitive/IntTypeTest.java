package com.meti.node.primitive;

import com.meti.node.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntTypeTest {

    @Test
    void render() {
        Type type = new IntType();
        assertEquals("int", type.render());
    }
}