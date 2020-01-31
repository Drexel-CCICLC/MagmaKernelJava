package com.meti;

import com.meti.primitive.IntNode;
import com.meti.primitive.IntType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeclareNodeTest {

    @Test
    void render() {
        Node value = new IntNode(10);
        Node node = new DeclareNode(IntType.INSTANCE, "test", value);
        assertEquals("int test=10;", node.render());
    }
}