package com.meti.node.value.primitive.array;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Parser;
import com.meti.node.Type;

import java.util.Optional;

public class ArraySizeParser implements Parser {
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
            Node size = compiler.parseSingle(sizeString);
            return Optional.of(new ArraySizeNode(type, size));
        }
        return Optional.empty();
    }

}
