package com.meti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MagmaInterpreter implements Interpreter {
    private final Collection<String> headers;
    private final ArrayList<Node> functions = new ArrayList<>();
    private final Declarations declarations = new Declarations();
    private final Parser rootParser = new MagmaParser(declarations, functions);
    private final Resolver rootResolver = new MagmaResolver(declarations);
    private final Compiler compiler = new UnitCompiler(rootParser, rootResolver);
    private final Interpreter parent = new CInterpreter();

    MagmaInterpreter(Collection<String> headers) {
        this.headers = headers;
    }

    @Override
    public String run(String content) throws IOException, InterruptedException {
        String headerString = headers.stream()
                .map(header -> "#include <" + header + ">\n")
                .collect(Collectors.joining());
        List<String> partitions = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        int depth = 0;
        for (char c : content.toCharArray()) {
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
        String compileString = partitions.stream()
                .filter(partition -> !partition.isBlank())
                .map(compiler::parse)
                .map(Node::render)
                .collect(Collectors.joining());
        String functionString = functions.stream()
                .map(Node::render)
                .collect(Collectors.joining());
        String output = headerString + functionString + compileString;
        return parent.run(output);
    }
}
