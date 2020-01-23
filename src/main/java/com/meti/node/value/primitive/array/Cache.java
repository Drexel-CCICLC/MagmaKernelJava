package com.meti.node.value.primitive.array;

import com.meti.node.Node;
import com.meti.node.bracket.struct.GeneratedNodeBuilder;

public interface Cache {
	void addFirst(GeneratedNodeBuilder builder);

	void addFirst(Node node);

    void addLast(Node node);

	String render();
}
