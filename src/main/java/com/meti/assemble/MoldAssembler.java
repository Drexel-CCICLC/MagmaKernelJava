package com.meti.assemble;

import com.meti.lex.Token;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MoldAssembler implements Assembler {
    private final Set<? extends NodeMoldFactory> moldFactories;

    MoldAssembler(Set<? extends NodeMoldFactory> moldFactories) {
        this.moldFactories = moldFactories;
    }

    @Override
    public Stream<Node> assemble(Stream<Token<?>> tokens) {
        List<Token<?>> tokenList = tokens.collect(Collectors.toList());
        return moldFactories.stream()
                .map(NodeMoldFactory::create)
                .peek(mold -> mold.pourAll(tokenList.stream()))
                .map(nodeMold -> nodeMold.set(this))
                .flatMap(Optional::stream);
    }
}
