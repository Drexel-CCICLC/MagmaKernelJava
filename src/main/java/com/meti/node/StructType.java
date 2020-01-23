package com.meti.node;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface StructType extends NamedType {
	static Type create(Type returnType, String name, List<? extends Type> parameters) {
		return new StructTypeImpl(returnType, name, parameters);
	}

	Optional<Type> returnType();

	class StructTypeImpl implements StructType {
		private final String name;
		private final List<? extends Type> parameters;
		private final Type returnType;

		private StructTypeImpl(Type returnType, String name, List<? extends Type> parameters) {
			this.returnType = returnType;
			this.name = name;
			this.parameters = parameters;
		}

		@Override
		public Optional<Type> returnType() {
			return Optional.of(returnType);
		}

		@Override
		public boolean isNamed() {
			return true;
		}

		@Override
		public String render() {
			String joinedParams = parameters.stream()
					.map(Type::render)
					.collect(Collectors.joining(","));
			return returnType.render() + "(*" + name + ")" + "(" + joinedParams + ")";
		}

		@Override
		public String renderWithName(String name) {
			return (isNamed()) ? render() : render() + " " + name;
		}


	}
}
