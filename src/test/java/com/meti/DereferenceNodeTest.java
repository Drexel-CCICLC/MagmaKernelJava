package com.meti;

import com.meti.point.DereferenceNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DereferenceNodeTest {

    @Test
    void render() {
        Node value = new VariableNode("test");
        Node node = new DereferenceNode(value);
        assertEquals("*test", node.render());
    }
}