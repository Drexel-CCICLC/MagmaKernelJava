package com.meti.node.bracket.struct;

import com.meti.node.StructType;
import com.meti.node.Type;
import com.meti.node.other.VoidType;

import java.util.ArrayList;
import java.util.List;

public interface StructTypeBuilder {
	static StructTypeBuilder create() {
		return new StructTypeBuilderImpl();
	}

	Type build();

	StructTypeBuilder withName(String name);

	StructTypeBuilder withParameter(Type parameter);

	StructTypeBuilder withParameters(List<Type> parameters);

	StructTypeBuilder withReturnType(Type returnType);

	final class StructTypeBuilderImpl implements StructTypeBuilder {
		private final String name;
		private final List<Type> parameters;
		private final Type returnType;

		private StructTypeBuilderImpl() {
			this(VoidType.INSTANCE, null, new ArrayList<>());
		}

		private StructTypeBuilderImpl(Type returnType, String name, List<Type> parameters) {
			this.returnType = returnType;
			this.name = name;
			this.parameters = parameters;
		}

		@Override
		public StructTypeBuilder withReturnType(Type returnType) {
			return new StructTypeBuilderImpl(returnType, name, parameters);
		}

		@Override
		public StructTypeBuilder withName(String name) {
			return new StructTypeBuilderImpl(returnType, name, parameters);
		}

		@Override
		public StructTypeBuilder withParameter(Type parameter) {
			parameters.add(parameter);
			return this;
		}

		@Override
		public StructTypeBuilder withParameters(List<Type> parameters) {
			return new StructTypeBuilderImpl(returnType, name, parameters);
		}

		@Override
		public Type build() {
            return StructType.create(returnType, name, parameters);
		}
	}
}
