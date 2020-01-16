package com.meti;

import com.meti.struct.IncrementedGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MagmaInterpreter implements Interpreter {
	private final Declarations declarations = new Declarations();
	private final ArrayList<Node> functions = new ArrayList<>();
	private final Collection<String> headers;
	private final Interpreter parent = new CInterpreter();
	private final Parser rootParser = new MagmaParser(declarations, functions, new IncrementedGenerator());
	private final Resolver rootResolver = new MagmaResolver(declarations);
	private final Compiler compiler = new UnitCompiler(rootParser, rootResolver);

	MagmaInterpreter(Collection<String> headers) {
		this.headers = headers;
	}

	@Override
	public Optional<String> parse(String content) {
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
				.map(compiler::parseSingle)
				.map(Node::render)
				.collect(Collectors.joining());
		String functionString = functions.stream()
				.map(Node::render)
				.collect(Collectors.joining());
		String output = headerString + functionString + compileString;
		return Optional.of(output);
	}

	@Override
	public String run(String content) throws IOException, InterruptedException {
        return parent.run(parse(content).orElseThrow());
	}
}
