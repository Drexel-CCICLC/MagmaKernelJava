package com.meti.array;

import com.meti.MagmaInterpreter;
import com.meti.Node;
import com.meti.struct.Generator;
import com.meti.struct.IncrementedGenerator;
import com.meti.struct.StructNodeBuilder;
import com.meti.struct.StructParser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Functions {
    private final List<Node> functions;

    public Functions() {
        this(new ArrayList<>());
	}

    public Functions(List<Node> functions) {
        this.functions = functions;
    }

    void add(StructNodeBuilder builder) {
        Generator generator = new IncrementedGenerator();
        add(builder, generator);
    }

    public void add(StructNodeBuilder builder, Generator generator) {
        Node node = builder.create(generator);
        functions.add(node);
    }

    public int size() {
		return functions.size();
	}

	public Stream<Node> stream() {
		return functions.stream();
	}
}
