package com.meti.struct;

import com.meti.Node;
import com.meti.Type;

import java.util.Map;
import java.util.stream.Collectors;

public class StructNode implements Node {
    private final Type returnType;
    private final String name;
    private final Map<String, Type> parameters;
    private final Node block;

    public StructNode(Type returnType, String name, Map<String, Type> parameters, Node block) {
        this.returnType = returnType;
        this.name = name;
        this.parameters = parameters;
        this.block = block;
    }

    @Override
    public String render() {
        String paramString = parameters.keySet()
                .stream()
                .map(s -> s + " " + parameters.get(s))
                .collect(Collectors.joining(","));
        return returnType.render() + " " + name + "(" + paramString + ")" + block.render();
    }

    @Override
    public boolean isParent() {
		return false;
    }
}
