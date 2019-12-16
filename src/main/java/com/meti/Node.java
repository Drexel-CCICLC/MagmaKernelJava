package com.meti;

import java.util.Optional;

public interface Node {
	String compile(Aliaser aliaser, NodeTree tree);

	Optional<Node> getParent();

	void setParent(Node parent);

	Struct struct();

	Node transform();
}
