package com.meti;

import java.util.*;
import java.util.function.Supplier;

import static com.meti.TreeDeclarationBuilder.create;

public class TreeDeclarations implements Declarations {
    private final Declaration ROOT = buildRoot();
    private final Stack<String> stack = new Stack<>();

    private TreeDeclaration buildRoot() {
        return create().withType(new RootType())
                .withName("root")
                .build();
    }

    @Override
    public Declaration absolute(Collection<String> names) {
        return names.stream().reduce(ROOT,
                (parent, childName) -> parent.child(childName).orElseThrow(),
                (parent, child) -> child);
    }

    @Override
    public Declaration define(DeclarationBuilder builder) {
        Declaration current = absolute(stack);
        Declaration declaration = current.define(builder);
        stack.push(declaration.render());
        return declaration;
    }

    @Override
    public Declaration parent() {
        return stack.isEmpty() ? ROOT : absolute(stack.subList(0, stack.size() - 1));
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

    @Override
    public String complete(Supplier<String> action) {
        String toReturn = action.get();
        stack.pop();
        return toReturn;
    }

    @Override
    public String renderInParentScope(Supplier<String> action) {
        String current = stack.pop();
        String toReturn = action.get();
        stack.push(current);
        return toReturn;
    }

    private static class RootType implements Type {
        @Override
        public String render() {
            throw new UnsupportedOperationException("Is root type.");
        }
    }
}
