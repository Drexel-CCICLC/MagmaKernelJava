package com.meti.node.transform;

import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.primitive.IntType;
import com.meti.node.primitive.StringType;

import java.util.function.Function;

public enum Operations implements Operation {
    ADD("+", "+"),
    SUBTRACT("-", "-"),
    EQUALS("==", "=="),
    NOT_EQUALS("!=", "!="),
    LESS_THAN("<", "<");

    private final String from;
    private final String to;

    Operations(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean isPresent(String content) {
        return content.contains(from);
    }

    @Override
    public String render(Node node0, Node node1) {
        return node0.render() + to + node1.render();
    }

    @Override
    public Node toNode(String content, Function<? super String, ? extends Node> parser) {
        int fromIndex = content.indexOf(from);
        String before = content.substring(0, fromIndex).trim();
        String after = content.substring(fromIndex + from.length()).trim();
        Node beforeNode = parser.apply(before);
        Node afterNode = parser.apply(after);
        return new OperationNode(beforeNode, afterNode, this);
    }

    @Override
    public Type toType(String content, Function<? super String, ? extends Type> applicator) {
        int fromIndex = content.indexOf(from);
        String beforeString = content.substring(0, fromIndex).trim();
        String afterString = content.substring(fromIndex + from.length()).trim();
        Type before = applicator.apply(beforeString);
        Type after = applicator.apply(afterString);
        if (before.equals(after)) {
            return before;
        } else {
            if (isAny(before, after, StringType.INSTANCE) && isAny(before, after, IntType.INSTANCE)) {
                return StringType.INSTANCE;
            }
            throw new UnsupportedOperationException();
        }
    }

    private boolean isAny(Type t0, Type t1, Type expected) {
        return t0.equals(expected) || t1.equals(expected);
    }
}
