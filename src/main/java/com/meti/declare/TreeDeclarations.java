package com.meti.declare;

import com.meti.node.Node;
import com.meti.node.Type;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TreeDeclarations implements Declarations {
    private final Declaration root = new SimpleTreeDeclaration("root", null, null);
    private final Stack<String> stack = new Stack<>();

    @Override
    public Declaration root() {
        return root;
    }

    @Override
    public Node define(String name, Supplier<? extends Node> action, DeclarationBuilder builder) {
        define(name, builder);
        stack.push(name);
        Node result = action.get();
        stack.pop();
        return result;
    }

    @Override
    public void define(String name, DeclarationBuilder builder) {
        current().define(builder);
    }

    @Override
    public Declaration current() {
        return absolute(stack);
    }

    private Declaration absolute(Iterable<String> values) {
        Declaration current = root;
        for (String value : values) {
            current = current.child(value)
                    .orElseThrow();
        }
        return current;
    }

    @Override
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

    @Override
    public Stream<Declaration> stream() {
        Collection<Declaration> declarations = new ArrayList<>();
        Deque<String> reverseStack = new LinkedList<>(stack);
        while (!reverseStack.isEmpty()) {
            declarations.add(absolute(reverseStack));
            reverseStack.pollLast();
        }
        declarations.add(root);
        return declarations.stream();
    }

    @Override
    public void define(String name, Type type, Runnable action) {
        define(name, DeclarationBuilder.create()
                .withName(name)
                .withType(type)
                .flagAsParameter());
        stack.push(name);
        action.run();
        stack.pop();
    }
}
