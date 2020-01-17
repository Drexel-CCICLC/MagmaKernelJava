package com.meti.declare;

import com.meti.node.Type;

public interface DeclarationBuilder {
    static DeclarationBuilder create() {
        return new DeclarationBuilderImpl();
    }

    DeclarationBuilder withName(String name);

    DeclarationBuilder withType(Type type);

    DeclarationBuilder flagAsParameter();

    Declaration build(Declaration parent);

    final class DeclarationBuilderImpl implements DeclarationBuilder {
        private final String name;
        private final Type type;
        private final boolean parameter;

        private DeclarationBuilderImpl() {
            this(null, null, false);
        }

        private DeclarationBuilderImpl(String name, Type type, boolean parameter) {
            this.name = name;
            this.type = type;
            this.parameter = parameter;
        }

        @Override
        public DeclarationBuilder withName(String name) {
            return new DeclarationBuilderImpl(name, type, parameter);
        }

        @Override
        public DeclarationBuilder withType(Type type) {
            return new DeclarationBuilderImpl(name, type, parameter);
        }

        @Override
        public DeclarationBuilder flagAsParameter() {
            return new DeclarationBuilderImpl(name, type, true);
        }

        @Override
        public Declaration build(Declaration parent) {
            return parameter ?
                    new SimpleTreeDeclaration(name, type, parent) :
                    new ParameterTreeDeclaration(name, type, parent);
        }
    }
}
