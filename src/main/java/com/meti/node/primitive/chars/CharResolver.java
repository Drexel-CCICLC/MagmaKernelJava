package com.meti.node.primitive.chars;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.exception.ParseException;
import com.meti.node.Type;

import java.util.Optional;

public class CharResolver implements Resolver {
    @Override
    public Optional<Type> resolveName(String content, Compiler compiler) {
        String trim = content.trim();
        if (trim.equals("Char")) {
            return Optional.of(CharType.INSTANCE);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Type> resolveValue(String content, Compiler compiler) {
        String trim = content.trim();
        if (trim.startsWith("'") && trim.endsWith("'")) {
            if (3 == trim.length()) {
                return Optional.of(CharType.INSTANCE);
            } else {
                throw new ParseException(trim + " has too many characters.");
            }
        }
        return Optional.empty();
    }
}
