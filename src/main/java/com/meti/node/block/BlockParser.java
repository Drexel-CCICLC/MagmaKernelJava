package com.meti.node.block;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlockParser implements Parser {
    @Override
    public Optional<Node> parse(String content, Compiler compiler) {
        String trim = content.trim();
        if (trim.startsWith("{") && trim.endsWith("}")) {
            String childString = trim.substring(1, trim.length() - 1);
            Collection<String> partitions = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            int depth = 0;
            for (char c : childString.toCharArray()) {
                if (c == ';' && depth == 0) {
                    partitions.add(builder.toString());
                    builder = new StringBuilder();
                } else {
                    if (c == '{') depth++;
                    if (c == '}') depth--;
                    builder.append(c);
                }
            }
            partitions.add(builder.toString());
            List<Node> children = partitions.stream()
                    .filter(s -> !s.isBlank())
                    .map(compiler::parse)
                    .collect(Collectors.toList());
            return Optional.of(new BlockNode(children));
        }
        return Optional.empty();
    }
}
