package com.meti.node.value.primitive.array;

import com.meti.node.Node;
import com.meti.node.bracket.struct.GeneratedNodeBuilder;

import java.util.stream.Stream;

public interface Cache {
    void add(GeneratedNodeBuilder builder);

    void add(Node node);

    Stream<Node> stream();
}
