package com.meti.node.condition;

import com.meti.node.Node;

public class IfNode implements Node {
    private final Node condition;
    private final Node block;

    public IfNode(Node condition, Node block) {
        this.condition = condition;
        this.block = block;
    }

    @Override
    public String render() {
        return "if(" + condition.render() + ")" + block.render();
    }
}
