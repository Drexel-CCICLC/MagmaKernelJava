package com.meti.node.block;

import com.meti.node.Node;

public class WhileNode implements Node {
    private final Node condition;
    private final Node block;

    public WhileNode(Node condition, Node block) {
        this.condition = condition;
        this.block = block;
    }

    @Override
    public String render() {
        return "while(" + condition.render() + ")" + block.render();
    }
}
