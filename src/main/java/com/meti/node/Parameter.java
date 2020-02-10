package com.meti.node;

import com.meti.parse.Declaration;
import com.meti.parse.ParameterDeclaration;

import java.util.Collections;
import java.util.List;

public interface Parameter {
	static Parameter create(Type type, String name) {
		return create(type, Collections.singletonList(name));
	}

	static Parameter create(Type type, List<String> name) {
		return new ParameterImpl(type, name);
	}

	String name();

	String render();

	Declaration toDeclaration();

	final class ParameterImpl implements Parameter {
		private final List<String> stack;
		private final Type type;

		private ParameterImpl(Type type, List<String> stack) {
			this.type = type;
			this.stack = stack;
		}

		@Override
		public String name() {
			return stack.get(stack.size() - 1);
		}

		@Override
		public String render() {
			return type.render(name());
		}

		@Override
		public Declaration toDeclaration() {
			return new ParameterDeclaration(stack, type);
		}
	}
}
