package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReferenceNodeTest {

    @Test
    void render() {
        Node value = new VariableNode("test");
        Node node = new ReferenceNode(value);
        assertEquals("&test", node.render());
    }
}