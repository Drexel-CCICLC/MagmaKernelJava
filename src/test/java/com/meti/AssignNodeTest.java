package com.meti;

import com.meti.primitive.IntNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssignNodeTest {

    @Test
    void render() {
        Node from = new IntNode(10);
        Node to = new VariableNode("x");
        Node node = new AssignNode(from, to);
        assertEquals("x=10i;", node.render());
    }
}