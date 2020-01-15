package com.meti;

public class UnitCompiler implements Compiler {
    private final Parser rootParser;
    private final Resolver rootResolver;

    public UnitCompiler(Parser root, Resolver rootResolver) {
        this.rootParser = root;
        this.rootResolver = rootResolver;
    }

    @Override
    public Node parse(String value) {
        return rootParser.parse(value, this).orElseThrow(() -> new ParseException("Failed to parse value:\n" + value));
    }

    @Override
    public Type resolveName(String name) {
        return rootResolver.resolveName(name, this).orElseThrow(() -> new ParseException("Failed to resolve name of value:\n" + name));
    }

    @Override
    public Type resolveValue(String value) {
        return rootResolver.resolveValue(value, this).orElseThrow(() -> new ParseException("Failed to resolve type of value:\n" + value));
    }
}
