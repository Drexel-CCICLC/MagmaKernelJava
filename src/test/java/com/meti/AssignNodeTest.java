package com.meti;

import com.meti.declare.AssignNode;
import com.meti.declare.VariableNode;
import com.meti.primitive.IntNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssignNodeTest {

    @Test
    void render() {
        Node from = new IntNode(10);
        Node to = new VariableNode("x");
        Node node = new AssignNode(to, from);
        assertEquals("x=10;", node.render());
    }
}