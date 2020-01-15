package com.meti.invoke;

import com.meti.Node;
import com.meti.ParseException;
import com.meti.Type;
import com.meti.other.VoidType;

import java.util.List;
import java.util.stream.Collectors;

public class InvocationNode implements Node {
    private final Node caller;
    private final List<? extends Node> args;
    private Type callerType;

    public InvocationNode(Type callerType, Node caller, List<? extends Node> args) {
        this.callerType = callerType;
        this.caller = caller;
        this.args = args;
    }

    @Override
    public String render() {
        if (callerType.returnType().isPresent()) {
            Type returnType = callerType.returnType().get();
            String end = returnType instanceof VoidType ? ";" : "";
            String argsString = args.stream()
                    .map(Node::render)
                    .collect(Collectors.joining(","));
            return caller.render() + "(" + argsString + ")" + end;
        } else {
            throw new ParseException(caller.render() + " is not a struct.");
        }
    }

    @Override
    public boolean isParent() {
        return false;
    }
}
