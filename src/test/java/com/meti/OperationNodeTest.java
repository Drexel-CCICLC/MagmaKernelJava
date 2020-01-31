package com.meti;

import com.meti.primitive.IntNode;
import com.meti.transform.OperationNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationNodeTest {

    @Test
    void render() {
        Node value0 = new IntNode(10);
        Node value1 = new IntNode(20);
        Node node = new OperationNode(value0, value1, Operation.ADD);
        assertEquals("10+20", node.render());
    }
}