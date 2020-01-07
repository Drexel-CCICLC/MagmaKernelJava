package com.meti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StructUnit implements Unit {
    private final StringBuilder callback;
    private final Declarations declarations;

    public StructUnit(StringBuilder callback, Declarations declarations) {
        this.callback = callback;
        this.declarations = declarations;
    }

    @Override
    public boolean canCompile(String value) {
        int equals = value.indexOf('=');
        int returnIndex = value.indexOf("=>");
        return value.contains(":") || (returnIndex != -1 && returnIndex <= equals);
    }

    @Override
    public String compile(String value, Compiler compiler) {
        String returnType = returnType(value, compiler).render();
        String name = declarations.stack().lastElement();
        String paramString = "(" + paramString(value, compiler) + ")";
        int index = value.indexOf(':');
        if (index != -1) {
            String content = value.substring(index + 1);
            if (name.equals("main")) {
                String formattedName = "main";
                String temp = declarations.pop();
                String result = returnType + " " + formattedName + paramString + compiler.compileOnly(content);
                declarations.push(temp);
                callback.append(result);
            } else {
                String result = returnType + " " + name + paramString + compiler.compileOnly(content);
                callback.append(result);
            }
        }
        return "";
    }

    private String paramString(String value, Compiler compiler) {
        int start = value.indexOf('(');
        if (start == -1) return "";
        int end = value.indexOf(')');
        String[] params = value.substring(start + 1, end).split(",");
        StringBuilder builder = new StringBuilder();
        for (String param : params) {
            if (!param.isBlank()) {
                int last = param.lastIndexOf(' ');
                String type = param.substring(0, last);
                String name = param.substring(last + 1);
                Type typeInstance = compiler.resolveName(type);
                String paramType = typeInstance.render();
                declarations.defineSibling(name, typeInstance, new ArrayList<>());
                builder.append(paramType)
                        .append("* ")
                        .append(name);
            }
        }

        return builder.toString();
    }

    private List<Type> paramTypeString(String value, Compiler compiler) {
        int start = value.indexOf('(');
        if (start == -1) {
            return Collections.emptyList();
        }
        int end = value.indexOf(')');
        String[] params = value.substring(start + 1, end).split(",");
        List<Type> types = new ArrayList<>();
        for (String param : params) {
            if (!param.isBlank()) {
                int space = param.lastIndexOf(' ');
                types.add(compiler.resolveName(param.substring(0, space)));
            }
        }
        return types;
    }

    @Override
    public Optional<Type> resolveName(String value, Compiler compiler) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> resolveValue(String value, Compiler compiler) {
        if (canCompile(value)) {
            Type returnType = returnType(value, compiler);
            String currentName = declarations.stack().lastElement();
            List<Type> params = paramTypeString(value, compiler);
            String paramString = params.stream()
                    .map(Type::render)
                    .map(s -> s + "*")
                    .collect(Collectors.joining(","));
            return Optional.of(returnType.render() + "(*" + currentName + ")(" + paramString + ")")
                    .map(s -> new Type(s, returnType, params));
        }
        return Optional.empty();
    }

    private Type returnType(String value, Compiler compiler) {
        int index = value.indexOf(':');
        if (index == -1) index = value.length();
        String header = value.substring(0, index);
        int returnIndex = header.indexOf("=>");
        return compiler.resolveName(header.substring(returnIndex + 2));
    }
}
