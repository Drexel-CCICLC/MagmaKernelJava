package com.meti;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.meti.Flag.VAL;
import static com.meti.Flag.VAR;
import static com.meti.TreeDeclarationBuilder.create;

public class DeclareUnit implements CompoundUnit {
    private final Declarations declarations;

    public DeclareUnit(Declarations declarations) {
        this.declarations = declarations;
    }

    @Override
    public boolean canCompile(String value) {
        int equals = index(value);
        if (equals == -1) return false;
        List<Flag> flags = parseFlags(value);
        return flags.contains(VAL) || flags.contains(VAR);
    }

    private int index(String value) {
        return value.indexOf('=');
    }

    private List<Flag> parseFlags(String value) {
        int equals = index(value);
        String flagString = value.substring(0, equals);
        int lastSpace = flagString.lastIndexOf(' ');
        flagString = flagString.substring(0, lastSpace);
        String[] flags = flagString.split(" ");
        return boxFlags(flags);
    }

    private List<Flag> boxFlags(String[] flags) {
        return Arrays.stream(flags)
                .map(String::toUpperCase)
                .map(Flag::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public String compile(String trim, Compiler compiler) {
        String value = parseValue(trim);
        DeclarationBuilder builder = build(trim, compiler);
        Declaration declaration = declarations.define(builder);
        return declarations.complete(() -> declaration.render(compiler, value));
    }

    private DeclarationBuilder build(String trim, Compiler compiler) {
        String name = parseName(trim);
        List<Flag> flags = parseFlags(trim);
        String value = parseValue(trim);
        Type valueType = compiler.resolveValue(value);
        return create().withName(name)
                .withFlags(flags)
                .withType(valueType);
    }

    private String parseValue(String trim) {
        int index = index(trim);
        return trim.substring(index + 1);
    }

    private String parseName(String trim) {
        int index = index(trim);
        String first = trim.substring(0, index).trim();
        int lastSpace = first.lastIndexOf(' ');
        return first.substring(lastSpace + 1);
    }

    @Override
    public Optional<? extends Type> resolveName(String value, Compiler compiler) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> resolveValue(String value, Compiler compiler) {
        return Optional.empty();
    }
}
