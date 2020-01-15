package com.meti.block;

import com.meti.Node;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BlockNode implements Node {
    private final Collection<Node> children;

    public BlockNode(Collection<Node> children) {
        this.children = children;
    }

    @Override
    public boolean isParent() {
        return true;
    }

    @Override
    public String render() {
        return children.stream()
                .map(Node::render)
                .collect(Collectors.joining());
    }
}
