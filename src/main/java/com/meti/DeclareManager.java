package com.meti;

import com.meti.exception.DoesNotExistException;
import com.meti.type.Type;
import com.meti.unit.Declaration;

import java.util.*;

public class DeclareManager {
	private final Declaration root;
	private final Stack<String> stack = new Stack<>();

	public DeclareManager() {
		this(new DeclarationImpl(Collections.emptySet(), null));
	}

	public DeclareManager(Declaration root) {
		this.root = root;
	}

	public void define(String name, Type type, Collection<String> flags) {
		current().define(name, type, flags);
	}

	public Declaration current() {
		return absolute(stack);
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
		current().delete(name);
	}

	public Declaration relative(String name) {
		return relativeOptionally(name)
				.orElseThrow(() -> new DoesNotExistException(name + " is not defined in scope."));
	}

	public Optional<Declaration> relativeOptionally(String name) {
		Queue<String> copy = new PriorityQueue<>(stack);
		while (!copy.isEmpty()) {
			Declaration frame = absolute(copy);
			Optional<Declaration> child = frame.child(name);
			if (child.isPresent()) {
				return child;
			} else {
				copy.poll();
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
		private final Map<String, Declaration> children;
		private final Collection<String> flags;
		private final Type type;

		public DeclarationImpl(Collection<String> flags, Type type) {
			this(flags, type, new LinkedHashMap<>());
		}

		public DeclarationImpl(Collection<String> flags, Type type, LinkedHashMap<String, Declaration> children) {
			this.flags = flags;
			this.type = type;
			this.children = children;
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

		@Override
		public boolean hasFlag(String value) {
			return getFlags().contains(value);
		}

		@Override
		public boolean isMutable() {
			return hasFlag("val");
		}

		@Override
		public boolean isNative() {
			return hasFlag("native");
		}
	}
}
