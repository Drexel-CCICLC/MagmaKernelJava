package com.meti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InvocationUnit implements Unit {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private final List<String> primitives = List.of("int");
    private StringBuilder builder = new StringBuilder();
    private int counter = -1;

    @Override
    public boolean canCompile(String value) {
        return value.endsWith(")");
    }

    @Override
    public String compile(String value, Compiler compiler) {
        builder = new StringBuilder();
        int open = value.indexOf('(');
        String name = value.substring(0, open);
        String parameterString = value.substring(open + 1, value.length() - 1);
        Type struct = compiler.resolveValue(name);
        List<Type> parameters = struct.parameters();
        List<String> args = Arrays.stream(parameterString.split(","))
                .filter(string -> !string.isBlank())
                .collect(Collectors.toList());
        if (parameters.size() != args.size()) throw new CompileException("Invalid number of arguments.");
        String params = join(parameters, args, compiler);
        Type returnType = struct.returnType().orElseThrow();
        String end = (returnType.render().equals("void")) ? ";" : "";
        return builder.toString() + name + "(" + params + ")" + end;
    }

    private String join(List<Type> parameters, List<String> args, Compiler compiler) {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < parameters.size(); i++) {
            Type expectedType = parameters.get(i);
            String paramValue = args.get(i);
            Type actualType = compiler.resolveValue(paramValue);

            if (expectedType.render().equals(".")) {
                Type type = expectedType.child().orElseThrow();
                Optional<Type> childOptional = type.child();

                if (childOptional.isPresent()) {
                    Type child = childOptional.get();
                    if (child.equals(actualType) || child.render().equals("void*")) {
                        items.add("*" + compiler.compileOnly(paramValue));
                    } else {
                        throw new CompileException("Type mismatch.");
                    }
                } else {
                    throw new CompileException("Type mismatch.");
                }
            } else if (expectedType.equals(actualType)) {
                String name = actualType.render();
                String address;
                String compiledValue = compiler.compileOnly(paramValue);
                if (isPrimitive(paramValue)) {
                    counter++;
                    String tempName = ALPHABET.charAt(counter % ALPHABET.length()) + String.valueOf(counter);
                    builder.append(name)
                            .append(" ")
                            .append(tempName)
                            .append("=")
                            .append(compiledValue)
                            .append(";");
                    address = tempName;
                } else {
                    address = compiledValue;
                }
                items.add("&" + address);
            } else {
                Optional<Type> child = expectedType.child();
                if (child.isPresent() && child.get().equals(actualType)) {
                    items.add(compiler.compileOnly(paramValue));
                } else {
                    throw new CompileException("Type mismatch.");
                }
            }
        }
        return String.join(",", items);
    }

    private boolean isPrimitive(String value) {
        boolean containsLetter = false;
        for (char c : value.toCharArray()) {
            if (Character.isLetter(c)) {
                containsLetter = true;
            }
        }
        if (containsLetter) {
            boolean isString = value.startsWith("\"") && value.endsWith("\"");
            boolean isChar = value.startsWith("'") && value.endsWith("'");
            return isString || isChar;
        } else {
            return true;
        }
    }

    @Override
    public Optional<Type> resolveName(String value, Compiler compiler) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> resolveValue(String value, Compiler compiler) {
        return Optional.empty();
    }
}
