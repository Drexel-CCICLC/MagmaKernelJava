package com.meti.node.value.primitive.array;

import com.meti.node.Node;
import com.meti.node.bracket.struct.GeneratedNodeBuilder;
import com.meti.node.bracket.struct.Generator;
import com.meti.node.bracket.struct.IncrementedGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

public class ListedCache implements Cache {
    private final Collection<Node> functions = new ArrayList<>();
    private final Generator generator = new IncrementedGenerator();

    @Override
    public void add(GeneratedNodeBuilder builder) {
        add(builder.create(generator));
    }

    @Override
    public void add(Node node) {
        functions.add(node);
    }

    @Override
    public Stream<Node> stream() {
        return functions.stream();
    }
}
