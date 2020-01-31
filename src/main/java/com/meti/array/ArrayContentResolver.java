package com.meti.array;

import com.meti.Compiler;
import com.meti.ParseException;
import com.meti.Resolver;
import com.meti.Type;

import java.util.Optional;

public class ArrayContentResolver implements Resolver {
    @Override
    public Optional<Type> resolveName(String name, Compiler compiler) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> resolveValue(String value, Compiler compiler) {
        String trim = value.trim();
        if (trim.startsWith("Array")) {
            int first = trim.indexOf('<');
            int last = trim.indexOf('>');
            if (first == -1 || last == -1) {
                throw new ParseException(trim + " has an invalid type argument.");
            } else {
                String typeString = trim.substring(first + 1, last).trim();
                Type elementType = compiler.resolveName(typeString);
                return Optional.of(new IndexedArrayType(elementType));
            }
        }
        return Optional.empty();
    }
}
