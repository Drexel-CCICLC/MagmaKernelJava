package com.meti.node.bracket.struct;

import com.meti.declare.Parameter;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.other.VoidType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface FunctionNodeBuilder extends GeneratedNodeBuilder {
    static FunctionNodeBuilder create() {
        return new FunctionNodeBuilderImpl();
    }

    FunctionNodeBuilder withBlock(Node block);

    default FunctionNodeBuilder withParameters(List<? extends Parameter> parameters) {
        parameters.forEach(this::withParameter);
		return this;
    }

	FunctionNodeBuilder withParameter(Parameter parameter);

    FunctionNodeBuilder withReturnType(Type returnType);

    final class FunctionNodeBuilderImpl implements FunctionNodeBuilder {
        private final Node block;
        private final String name;
        private final Set<Parameter> parameters;
        private final Type returnType;

        private FunctionNodeBuilderImpl() {
            this(null, null, new HashSet<>(), null);
        }

        private FunctionNodeBuilderImpl(Node block, String name, Set<Parameter> parameters, Type returnType) {
            this.block = block;
            this.name = name;
            this.parameters = parameters;
            this.returnType = returnType;
        }

        @Override
        public Node create(Generator generator) {
            String name = buildName(generator);
            Type type = buildType();
            return new FunctionNode(name, parameters, type, block);
        }

        private String buildName(Generator generator) {
            return (null == this.name) ? generator.next() : this.name;
        }

        private Type buildType() {
            return null == this.returnType ? VoidType.INSTANCE() : this.returnType;
        }

        @Override
        public FunctionNodeBuilder withBlock(Node block) {
            return new FunctionNodeBuilderImpl(block, name, parameters, returnType);
        }

        @Override
        public FunctionNodeBuilder withName(String name) {
            return new FunctionNodeBuilderImpl(block, name, parameters, returnType);
        }

        @Override
        public FunctionNodeBuilder withParameter(Parameter parameter) {
            parameters.add(parameter);
            return this;
        }

        @Override
        public FunctionNodeBuilder withReturnType(Type returnType) {
            return new FunctionNodeBuilderImpl(block, name, parameters, returnType);
        }
    }
}
