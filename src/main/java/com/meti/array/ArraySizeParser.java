package com.meti.array;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;
import com.meti.Type;

import java.util.Optional;

class ArraySizeParser implements Parser {
    @Override
    public Optional<Node> parse(String value, Compiler compiler) {
        String trim = value.trim();
        if (trim.startsWith("Array")) {
            int typeStart = trim.indexOf('<') + 1;
            int typeEnd = trim.indexOf('>');
            String typeString = trim.substring(typeStart, typeEnd);
            Type type = compiler.resolveName(typeString);
            int sizeStart = trim.indexOf('(') + 1;
            int sizeEnd = trim.indexOf(')');
            String sizeString = trim.substring(sizeStart, sizeEnd);
            Node size = compiler.compile(sizeString);
            return Optional.of(new ArraySizeNode(type, size));
        }
        return Optional.empty();
    }
}
