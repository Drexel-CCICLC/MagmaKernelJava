package com.meti.node.condition;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;
import com.meti.util.ParenthesisPartitioner;
import com.meti.util.Partitioner;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class IfParser implements Parser {
    @Override
    public Optional<Node> parse(String content, Compiler compiler) {
        return Optional.of(content)
                .map(String::trim)
                .filter(s -> s.startsWith("if("))
                .map(s -> s.substring(3))
                .map(ParenthesisPartitioner::new)
                .map(Partitioner::partition)
                .map(strings -> mapToNodes(strings, compiler))
                .map(this::mapToIfNode);
    }

    private Node mapToIfNode(List<? extends Node> nodes) {
        return new IfNode(nodes.get(0), nodes.get(1));
    }

    private List<Node> mapToNodes(Collection<String> strings, Compiler compiler) {
        return strings.stream()
                .map(compiler::parse)
                .collect(Collectors.toList());
    }
}
