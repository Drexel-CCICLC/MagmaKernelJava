package com.meti.node.bracket.struct;

import com.meti.node.Type;
import com.meti.node.other.VoidType;

import java.util.ArrayList;
import java.util.List;

public interface StructTypeBuilder {
    static StructTypeBuilder create() {
        return new StructTypeBuilderImpl();
    }

    StructTypeBuilder withReturnType(Type returnType);

    StructTypeBuilder withName(String name);

    StructTypeBuilder withParameter(Type parameter);

    StructTypeBuilder withParameters(List<Type> parameters);

    Type build();

    final class StructTypeBuilderImpl implements StructTypeBuilder {
        private final Type returnType;
        private final String name;
        private final List<Type> parameters;

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
            return new StructType(returnType, name, parameters);
        }
    }
}
