package com.meti;

import com.meti.declare.Declaration;
import com.meti.declare.ParameterDeclaration;
import com.meti.struct.FunctionType;

public interface Parameter {
	static Parameter create(Type type, String name) {
		return new ParameterImpl(type, name);
	}

	String name();

	String render();

	Declaration toDeclaration();

	final class ParameterImpl implements Parameter {
		private final String name;
		private final Type type;

		private ParameterImpl(Type type, String name) {
			this.type = type;
			this.name = name;
		}

		@Override
		public String name() {
			return name;
		}

		@Override
		public String render() {
			if (type instanceof FunctionType) {
				return type.render();
			} else {
				return type.render() + " " + name;
			}
		}

		@Override
		public Declaration toDeclaration() {
			return new ParameterDeclaration(name, type);
		}
	}
}
