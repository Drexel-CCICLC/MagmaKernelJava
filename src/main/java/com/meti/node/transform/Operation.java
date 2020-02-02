package com.meti.node.transform;

import com.meti.node.Node;

import java.util.function.Function;

public interface Operation {
	boolean isPresent(String content);

	String render(Node node0, Node node1);

	Node toNode(String content, Function<? super String, ? extends Node> parser);
}
