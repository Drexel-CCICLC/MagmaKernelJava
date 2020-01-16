package com.meti.array;

import com.meti.Node;
import com.meti.struct.Generator;
import com.meti.struct.IncrementedGenerator;
import com.meti.struct.StructNodeBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

public class ListedFunctions implements Functions {
    private final Collection<Node> functions = new ArrayList<>();

    @Override
    public void add(StructNodeBuilder builder) {
        Generator generator = new IncrementedGenerator();
        add(builder, generator);
    }

    @Override
    public void add(StructNodeBuilder builder, Generator generator) {
        Node node = builder.create(generator);
        functions.add(node);
    }

    @Override
    public int size() {
        return functions.size();
    }

    @Override
    public Stream<Node> stream() {
        return functions.stream();
    }
}
