package com.meti.node;

import java.util.Deque;

//TODO: make ParentNode interface
public interface Node {
	Deque<Node> children();

	boolean isParent();

	String render();
}
