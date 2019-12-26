package com.meti;

import com.meti.exception.DoesNotExistException;
import com.meti.type.Type;
import com.meti.unit.Declaration;

import java.util.*;

public class DeclareManager {
	private final Declaration root = new DeclarationImpl(Collections.emptySet(), null);
	private final Stack<String> stack = new Stack<>();

	public void define(String name, Type type, Collection<String> flags) {
		absolute(stack).define(name, type, flags);
	}

	public Declaration absolute(Collection<String> splitName) {
		Declaration current = root;
		Collection<String> innerStack = new ArrayList<>();
		for (String name : splitName) {
			Optional<Declaration> optional = current.child(name);
			if (optional.isPresent()) {
				innerStack.add(name);
				current = optional.get();
			} else {
				String joinedNames = String.join(",", innerStack);
				throw new DoesNotExistException(joinedNames + " is not defined.");
			}
		}
		return current;
	}

	public void delete(String name) {
		absolute(stack).delete(name);
	}

	public Declaration relative(String name) {
		Queue<String> copy = new PriorityQueue<>(stack);
		while (!copy.isEmpty()) {
			Declaration frame = absolute(copy);
			Optional<Declaration> child = frame.child(name);
			if (child.isPresent()) {
				return child.get();
			}
		}
		throw new DoesNotExistException(name + " does not exist in scope.");
	}

	public void sink(String name) {
		stack.push(name);
	}

	public void surface() {
		stack.pop();
	}

	private static final class DeclarationImpl implements Declaration {
		private final Map<String, Declaration> children = new LinkedHashMap<>();
		private final Collection<String> flags;
		private final Type type;

		public DeclarationImpl(Collection<String> flags, Type type) {
			this.flags = flags;
			this.type = type;
		}

		@Override
		public Optional<Declaration> child(String name) {
			return Optional.ofNullable(children.get(name));
		}

		@Override
		public Map<String, Declaration> children() {
			return children;
		}

		@Override
		public void define(String name, Type type, Collection<String> flags) {
			Declaration declaration = new DeclarationImpl(flags, type);
			children.put(name, declaration);
		}

		@Override
		public void delete(String name) {
			children.remove(name);
		}

		@Override
		public Collection<String> getFlags() {
			return flags;
		}

		@Override
		public Type getType() {
			return type;
		}
	}
}
