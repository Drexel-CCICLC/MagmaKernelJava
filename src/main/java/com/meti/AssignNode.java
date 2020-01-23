package com.meti;

public class AssignNode implements Node {
    private final Node from;
    private final Node to;

    public AssignNode(Node from, Node to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String render() {
        return to.render() + "=" + from.render() + ";";
    }
}
