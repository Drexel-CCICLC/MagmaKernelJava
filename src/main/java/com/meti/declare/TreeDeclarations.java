package com.meti.declare;

import com.meti.node.Node;
import com.meti.node.Type;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TreeDeclarations implements Declarations {
	private final Declaration root;
	private final Stack<String> stack;

	public TreeDeclarations() {
		this(new Stack<>());
	}

	private TreeDeclarations(Stack<String> stack) {
		this(stack, TreeDeclarationBuilder.create()
				.withType(null)
				.withParameter(false)
				.withStack(new Stack<>()));
	}

	TreeDeclarations(Stack<String> stack, TreeDeclarationBuilder root) {
		this.stack = stack;
		this.root = root.withDeclarations(this)
				.build();
	}

	@Override
	public boolean isCurrent(Declaration obj) {
		return current().equals(obj);
	}

	@Override
	public boolean isRoot(Declaration obj) {
		return root.equals(obj);
	}

	@Override
	public Optional<Declaration> parentOf(String name) {
		return stream()
				.filter(declaration -> declaration.child(name).isPresent())
				.findFirst();
	}

	@Override
	public <T> T define(String name, Type type, Supplier<? extends T> action) {
		define(name, type);
		stack.push(name);
		T result = action.get();
		stack.pop();
		return result;
	}

	@Override
	public Declaration define(String name, Type type) {
		return current().define(name, type);
	}

	@Override
	public Declaration current() {
		return absolute(stack);
	}

	@Override
	public Declaration absolute(Collection<String> values) {
		return values.stream().reduce(root,
				(declaration, s) -> declaration.child(s).orElseThrow(),
				(declaration, declaration2) -> declaration2);
	}

	@Override
	public Optional<Declaration> relative(String value) {
		Deque<String> reverseStack = new LinkedList<>(stack);
		while (!reverseStack.isEmpty()) {
			Optional<Declaration> optional = absolute(reverseStack).child(value);
			if (optional.isPresent()) return optional;
			reverseStack.pollLast();
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
		stack.push(name);
		define(name, type);
		action.run();
		stack.pop();
	}

	@Override
	public void define(Parameter parameter) {
		current().define(parameter);
	}
}
