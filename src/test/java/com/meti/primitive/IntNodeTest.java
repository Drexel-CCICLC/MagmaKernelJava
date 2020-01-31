package com.meti.primitive;

import com.meti.Node;
import com.meti.primitive.IntNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntNodeTest {
    @Test
    void test10() {
        Node node = new IntNode(10);
        assertEquals("10", node.render());
    }

    @Test
    void test100() {
        Node node = new IntNode(100);
        assertEquals("100", node.render());
    }

}
