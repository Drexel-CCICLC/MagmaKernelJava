package com.meti;

import com.meti.exception.DoesNotExistException;
import com.meti.type.Type;
import com.meti.unit.Declaration;

import java.util.*;

import static java.util.Collections.emptySet;

public class TreeDeclarations implements Declarations {
	private final Declaration root;
	private final Stack<String> stack = new Stack<>();

	public TreeDeclarations() {
		this(new TreeDeclaration(emptySet(), null));
	}

	public TreeDeclarations(Declaration root) {
		this.root = root;
	}

	@Override
	public Declaration define(String name, Type type, Collection<String> flags) {
		return current().define(name, type, flags);
	}

	@Override
	public Declaration current() {
		return absolute(stack);
	}

	@Override
	public Declaration absolute(Collection<String> splitName) {
		Declaration current = root;
		Collection<String> innerStack = new ArrayList<>();
		for (String name : splitName) {
			Optional<Declaration> optional = current.child(name);
			if (optional.isPresent()) {
				innerStack.add(name);
				current = optional.get();
			} else {
				innerStack.add(name);
				String joinedNames = String.join(",", innerStack);
				throw new DoesNotExistException(joinedNames + " is not defined.");
			}
		}
		return current;
	}

	@Override
	public Declaration define(String name, Type type) {
		return define(name, type, emptySet());
	}

	@Override
	public void delete(String name) {
		current().delete(name);
	}

	@Override
	public Declaration relative(String name) {
		return relativeOptionally(name)
				.orElseThrow(() -> new DoesNotExistException(name + " is not defined in scope."));
	}

	@Override
	public Optional<Declaration> relativeOptionally(String name) {
		Queue<String> copy = new PriorityQueue<>(stack);
		while (true) {
			Declaration frame = absolute(copy);
			Optional<Declaration> child = frame.child(name);
			if (child.isPresent()) {
				return child;
			} else {
				if (copy.isEmpty()) {
					break;
				}
				copy.poll();
			}
		}
		return Optional.empty();
	}

	@Override
	public void sink(String name) {
		stack.push(name);
	}

	@Override
	public void surface() {
		stack.pop();
	}


}
