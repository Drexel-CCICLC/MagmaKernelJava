package com.meti;

import com.meti.node.Node;

public interface Cache {
	void add(Node node);

	void addFunction(Node function);

	void addStruct(Node struct);

	String render();
}
