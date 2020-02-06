package com.meti.node.condition;

import com.meti.node.Node;

public class ElseNode implements Node {
    private final Node block;

    public ElseNode(Node block) {
        this.block = block;
    }

    @Override
    public String render() {
        return "else" + block.render();
    }
}
