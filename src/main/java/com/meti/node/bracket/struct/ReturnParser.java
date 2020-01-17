package com.meti.node.bracket.struct;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Parser;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReturnParser implements Parser {
    private Optional<Node> parse(String value, Compiler compiler) {
        String trim = value.trim();
        if (trim.startsWith("return ")) {
            String valueString = trim.substring(7);
            Node node = compiler.parseSingle(valueString);
            return Optional.of(new ReturnNode(node));
        }
        return Optional.empty();
    }

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return parse(value, compiler).stream().collect(Collectors.toSet());
	}
}