package com.meti;

import com.meti.primitive.IntNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuantityNodeTest {

    @Test
    void render() {
        Node value = new IntNode(10);
        Node node = new QuantityNode(value);
        assertEquals("(10i)", node.render());
    }
}