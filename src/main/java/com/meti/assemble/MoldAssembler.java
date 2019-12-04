package com.meti.assemble;

import com.meti.lex.Token;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MoldAssembler implements Assembler {
    private final List<? extends NodeMoldFactory> moldFactories;

    MoldAssembler(List<? extends NodeMoldFactory> moldFactories) {
        this.moldFactories = moldFactories;
    }

    @Override
    public Stream<Node> assemble(Stream<Token<?>> tokens) {
        List<Token<?>> tokenList = tokens.collect(Collectors.toList());
        return moldFactories.stream()
                .map((Function<NodeMoldFactory, NodeMold>) NodeMoldFactory::create)
                .peek(nodeMold -> nodeMold.pourAll(tokenList.stream()))
                .map(mold -> mold.set(this))
                .flatMap(Optional::stream)
                .findFirst()
                .stream();
    }
}
