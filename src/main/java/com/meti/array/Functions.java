package com.meti.array;

import com.meti.Node;
import com.meti.struct.Generator;
import com.meti.struct.StructNodeBuilder;

import java.util.stream.Stream;

public interface Functions {
    void add(StructNodeBuilder builder);

    void add(StructNodeBuilder builder, Generator generator);

    int size();

    Stream<Node> stream();
}
