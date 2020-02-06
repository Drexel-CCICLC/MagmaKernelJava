package com.meti.node.block;

import com.meti.node.Node;

import java.util.Collection;
import java.util.stream.Collectors;

public class BlockNode implements Node {
    private final Collection<? extends Node> children;

    public BlockNode(Collection<? extends Node> children) {
        this.children = children;
    }

    @Override
    public String render() {
        String joinedChildren = children.stream()
                .map(Node::render)
                .collect(Collectors.joining());
        return "{" + joinedChildren + "}";
    }
}
