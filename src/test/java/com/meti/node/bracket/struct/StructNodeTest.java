package com.meti.node.bracket.struct;

import com.meti.node.Node;
import com.meti.node.value.primitive.integer.IntType;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StructNodeTest {

    @RepeatedTest(3)
    void render() {
        Node node = new StructNode("Point", Map.of(
                "x", IntType.INSTANCE,
                "y", IntType.INSTANCE
        ));
        assertEquals("struct Point{int x;int y;}", node.render());
    }
}