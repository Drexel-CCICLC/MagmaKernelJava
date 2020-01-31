package com.meti;

import com.meti.declare.VariableNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VariableNodeTest {

    @Test
    void render() {
        Node node = new VariableNode("test");
        assertEquals("test", node.render());
    }
}