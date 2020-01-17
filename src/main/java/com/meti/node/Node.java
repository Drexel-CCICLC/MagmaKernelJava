package com.meti.node;

import java.util.Deque;

public interface Node {
	Deque<Node> children();

	boolean isParent();

	String render();
}
