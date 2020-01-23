package com;

import com.meti.IntNode;
import com.meti.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntNodeTest {
    @Test
    void test10() {
        Node node = new IntNode(10);
        assertEquals("10i", node.render());
    }

    @Test
    void test100() {
        Node node = new IntNode(100);
        assertEquals("100i", node.render());
    }

}
