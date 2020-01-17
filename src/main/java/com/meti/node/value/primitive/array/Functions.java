package com.meti.node.value.primitive.array;

import com.meti.node.Node;
import com.meti.node.bracket.struct.Generator;
import com.meti.node.bracket.struct.StructNodeBuilder;

import java.util.stream.Stream;

public interface Functions {
    void add(StructNodeBuilder builder);

    void add(StructNodeBuilder builder, Generator generator);

    int size();

    Stream<Node> stream();
}
