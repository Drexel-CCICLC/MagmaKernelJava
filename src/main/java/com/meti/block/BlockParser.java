package com.meti.block;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlockParser implements Parser {
    @Override
    public Optional<Node> parse(String value, Compiler compiler) {
        String trim = value.trim();
        if (trim.startsWith("{") && trim.endsWith("}")) {
            String childrenString = trim.substring(1, trim.length() - 1);
            List<String> childrenStrings = new ArrayList<>();
            StringBuilder buffer = new StringBuilder();
            int depth = 0;
            for (char c : childrenString.toCharArray()) {
                if (c == ';' && depth == 0) {
                    childrenStrings.add(buffer.toString());
                    buffer = new StringBuilder();
                } else {
                    if (c == '{') {
                        depth++;
                    }
                    if (c == '}') {
                        depth--;
                    }
                    buffer.append(c);
                }
            }
            childrenStrings.add(buffer.toString());
            List<Node> children = childrenStrings.stream()
                    .map(String::trim)
                    .filter(childString -> !childString.isEmpty())
                    .map(compiler::parseSingle)
                    .collect(Collectors.toList());
            return Optional.of(new BlockNode(children));
        }
        return Optional.empty();
    }

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return parse(value, compiler).stream().collect(Collectors.toSet());
	}
}
