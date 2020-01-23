package com.meti;

public class DeclareNode implements Node {
    private final Type type;
    private final Node value;
    private final String name;

    public DeclareNode(Type type, String name, Node value) {
        this.value = value;
        this.type = type;
        this.name = name;
    }

    @Override
    public String render() {
        return type.render() + " " + name + "=" +
                value.render() + ";";
    }
}
