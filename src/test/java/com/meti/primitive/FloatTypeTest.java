package com.meti.primitive;

import com.meti.Type;
import com.meti.primitive.FloatType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FloatTypeTest {

    @Test
    void render() {
        Type type = new FloatType();
        assertEquals("float", type.render());
    }
}