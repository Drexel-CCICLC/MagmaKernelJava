package com.meti.array;

import com.meti.Node;
import com.meti.struct.IncrementedGenerator;
import com.meti.struct.StructNodeBuilder;

import java.util.List;

public class Functions {
	private final List<Node> functions;

	public Functions(List<Node> functions) {
		this.functions = functions;
	}

	void add(StructNodeBuilder builder) {
		Node node = builder.create(new IncrementedGenerator());
		functions.add(node);
	}

	public List<Node> getFunctions() {
		return functions;
	}
}
