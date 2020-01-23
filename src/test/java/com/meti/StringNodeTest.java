package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringNodeTest {
    @Test
    void test() {
        Node node = new StringNode("Hello World!");
        assertEquals("\"Hello World!\"", node.render());
    }
}