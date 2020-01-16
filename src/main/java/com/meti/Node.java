package com.meti;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public interface Node {
	LinkedList<Node> children();

	boolean isParent();

	String render();
}
