package com.meti.invoke;

import com.meti.Node;

import java.util.List;
import java.util.stream.Collectors;

public class InvocationNode implements Node {
    private final Node caller;
    private final List<? extends Node> args;

    public InvocationNode(Node caller, List<? extends Node> args) {
        this.caller = caller;
        this.args = args;
    }

    @Override
    public String render() {
        String argsString = args.stream()
                .map(Node::render)
                .collect(Collectors.joining(","));
        return caller.render() + "(" + argsString + ")";
    }
}
