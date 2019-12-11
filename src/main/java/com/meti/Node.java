package com.meti;

public class Node {
	private final NodeStruct struct;
	private final NodeType type;

	public Node(NodeStruct struct, NodeType type) {
		this.struct = struct;
		this.type = type;
	}
}
