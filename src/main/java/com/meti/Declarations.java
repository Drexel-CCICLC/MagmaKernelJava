package com.meti;

import java.util.*;
import java.util.function.Supplier;

public class Declarations {
    private final Declaration root = new Declaration("root", null);
    private final Stack<String> stack = new Stack<>();

    public Declaration absolute(Collection<String> values) {
        Declaration current = root;
        for (String value : values) {
            current = current.child(value)
                    .orElseThrow();
        }
        return current;
    }

    public Declaration current() {
        return absolute(stack);
    }

    public Optional<Declaration> relative(String value) {
        Deque<String> reverseStack = new LinkedList<>(stack);
        while (!reverseStack.isEmpty()) {
            Optional<Declaration> optional = absolute(reverseStack).child(value);
            if (optional.isPresent()) {
                return optional;
            } else {
                reverseStack.pollLast();
            }
        }
        return root.child(value);
    }

    public void define(String name, Type type) {
        current().define(name, type);
    }

    public Node define(String name, Type type, Supplier<Node> action) {
        define(name, type);
        stack.push(name);
        Node result = action.get();
        stack.pop();
        return result;
    }
}
