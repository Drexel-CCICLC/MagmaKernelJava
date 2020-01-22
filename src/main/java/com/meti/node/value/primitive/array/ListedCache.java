package com.meti.node.value.primitive.array;

import com.meti.node.Node;
import com.meti.node.bracket.struct.Generator;
import com.meti.node.bracket.struct.IncrementedGenerator;
import com.meti.node.bracket.struct.FunctionNodeBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

public class ListedCache implements Cache {
    private final Collection<Node> functions = new ArrayList<>();

    @Override
    public void add(FunctionNodeBuilder builder) {
        Generator generator = new IncrementedGenerator();
        add(builder, generator);
    }

    @Override
    public void add(FunctionNodeBuilder builder, Generator generator) {
        Node node = builder.create(generator);
        functions.add(node);
    }

    @Override
    public Stream<Node> stream() {
        return functions.stream();
    }
}
