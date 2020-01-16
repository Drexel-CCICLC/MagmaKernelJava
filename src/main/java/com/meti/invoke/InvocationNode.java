package com.meti.invoke;

import com.meti.Node;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class InvocationNode implements Node {
    private final Collection<? extends Node> args;
    private final Node caller;
    private final boolean isVoid;

    public InvocationNode(Node caller, Collection<? extends Node> args) {
        this(caller, args, false);
	}

    public InvocationNode(Node caller, Collection<? extends Node> args, boolean isVoid) {
        this.caller = caller;
        this.args = args;
        this.isVoid = isVoid;
    }

    @Override
    public LinkedList<Node> children() {
        return new LinkedList<>();
    }

    @Override
    public boolean isParent() {
        return false;
    }

    @Override
    public String render() {
        String end = isVoid ? ";" : "";
        String argsString = args.stream()
                .map(Node::render)
                .collect(Collectors.joining(","));
        return caller.render() + "(" + argsString + ")" + end;
    }
}
