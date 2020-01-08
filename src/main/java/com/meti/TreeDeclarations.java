package com.meti;

import java.util.*;

public class TreeDeclarations implements Declarations {
    private final Declaration ROOT = new TreeDeclaration("root", new RootType(), new StringBuilder());
    private final Stack<String> stack = new Stack<>();

    @Override
    public Declaration absolute(Collection<String> names) {
        return names.stream().reduce(ROOT,
                (declaration, s) -> declaration.child(s).orElseThrow(),
                (declaration, declaration2) -> declaration2);
    }

    @Override
    public void defineSibling(String name, Type type, StringBuilder callback) {
        Declaration parent = parent();
        parent.define(name, type, callback);
    }

    @Override
    public Declaration parent() {
        return stack.isEmpty() ? ROOT : absolute(stack.subList(0, stack.size() - 1));
    }

    @Override
    public String pop() {
        return stack.pop();
    }

    @Override
    public void push(String name) {
        stack.push(name);
    }

    @Override
    public Optional<Declaration> relative(String name) {
        Deque<String> names = new LinkedList<>(stack);
        Declaration toReturn = null;
        do {
            Optional<Declaration> optional = remove(name, names);
            if (optional.isPresent()) toReturn = optional.get();
        } while (!names.isEmpty());
        return Optional.ofNullable(toReturn);
    }

    private Optional<Declaration> remove(String name, Deque<String> names) {
        Declaration absolute = absolute(names);
        Optional<Declaration> child = absolute.child(name);
        if (child.isEmpty()) names.pollLast();
        return child;
    }

    @Override
    public Declaration root() {
        return ROOT;
    }

    @Override
    public Stack<String> stack() {
        return stack;
    }

    private static class RootType implements Type {
        @Override
        public String render() {
            throw new UnsupportedOperationException("Is root type.");
        }
    }
}
