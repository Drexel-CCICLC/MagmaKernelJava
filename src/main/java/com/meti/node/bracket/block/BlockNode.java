package com.meti.node.bracket.block;

import com.meti.node.Node;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class BlockNode implements Node {
    private final LinkedList<Node> children;

    public BlockNode(Collection<? extends Node> children) {
        this.children = new LinkedList<>(children);
    }

	@Override
	public LinkedList<Node> children() {
		return children;
	}

	@Override
    public boolean isParent() {
        return true;
    }

    @Override
    public String render() {
        return "{" + children.stream()
                .map(Node::render)
                .collect(Collectors.joining()) + "}";
    }
}
