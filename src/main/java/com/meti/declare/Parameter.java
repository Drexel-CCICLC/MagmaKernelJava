package com.meti.declare;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.value.compound.variable.VariableNode;

public interface Parameter {
	static Parameter of(String content, Compiler compiler) {
		int lastIndex = content.lastIndexOf(' ');
		String typeString = content.substring(0, lastIndex);
		String name = content.substring(lastIndex + 1);
		Type type = compiler.resolveName(typeString);
		return of(name, type);
	}

	static Parameter of(String name, Type type) {
		return new ParameterImpl(type, name);
	}

	String render();

	Node toNode();

	Type type();

	final class ParameterImpl implements Parameter {
		private final String name;
		private final Type type;

		private ParameterImpl(Type type, String name) {
			this.type = type;
			this.name = name;
		}

		@Override
		public String render() {
			return type().render() + " " + name;
		}

		@Override
		public Node toNode() {
			return new VariableNode(name);
		}

		@Override
		public Type type() {
			return type;
		}
	}
}
