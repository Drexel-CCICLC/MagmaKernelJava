package com.meti.node.bracket.struct;

import com.meti.node.Node;
import com.meti.node.Type;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class StructNode implements Node {
    private final String name;
    private final Map<String, Type> fields;

    public StructNode(String name, Map<String, Type> fields) {
        this.name = name;
        this.fields = fields;
    }

    @Override
    public Deque<Node> children() {
        return new LinkedList<>();
    }

    @Override
    public boolean isParent() {
        return false;
    }

    @Override
    public String render() {
        String fieldsString = fields.keySet()
                .stream()
                .sorted()
                .map(s -> fields.get(s).render() + " " + s + ";")
                .collect(Collectors.joining());
        return "struct " + name + "{" + fieldsString + "};";
    }
}
