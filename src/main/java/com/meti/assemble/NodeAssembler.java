package com.meti.assemble;

import com.meti.lex.Token;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

class NodeAssembler implements Assembler {
    private final Set<? extends NodeMoldFactory> moldFactories;

    NodeAssembler(Set<? extends NodeMoldFactory> moldFactories) {
        this.moldFactories = moldFactories;
    }

    @Override
    public List<Node> assemble(List<Token<?>> tokens) {
        return moldFactories.stream()
                .map(NodeMoldFactory::create)
                .peek(mold -> mold.pourAll(tokens))
                .map(NodeMold::set)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }
}
