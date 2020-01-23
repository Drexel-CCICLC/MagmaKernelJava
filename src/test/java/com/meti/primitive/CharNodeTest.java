package com.meti.primitive;

import com.meti.Node;
import com.meti.primitive.CharNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharNodeTest {

    @Test
    void render() {
        Node node = new CharNode('a');
        assertEquals("'a'", node.render());
    }
}