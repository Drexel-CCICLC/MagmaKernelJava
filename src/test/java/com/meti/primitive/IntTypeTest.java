package com.meti.primitive;

import com.meti.Type;
import com.meti.primitive.IntType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntTypeTest {

    @Test
    void render() {
        Type type = new IntType();
        assertEquals("int", type.render());
    }
}