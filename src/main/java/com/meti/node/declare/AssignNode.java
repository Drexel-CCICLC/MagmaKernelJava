package com.meti.node.declare;

import com.meti.node.Node;

public class AssignNode implements Node {
    private final Node from;
    private final Node to;

    public AssignNode(Node to, Node from) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String render() {
        return to.render() + "=" + from.render() + ";";
    }
}
