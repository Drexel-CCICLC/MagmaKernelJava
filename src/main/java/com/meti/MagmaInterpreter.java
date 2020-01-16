package com.meti;

import com.meti.array.Functions;
import com.meti.struct.IncrementedGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class MagmaInterpreter implements Interpreter {
	private final Declarations declarations = new Declarations();
	private final ArrayList<Node> functions = new ArrayList<>();
	private final Collection<String> headers;
	private final Interpreter parent = new CInterpreter();
	private final Functions functions1 = new Functions(functions);
	private final Parser rootParser = new MagmaParser(declarations, new IncrementedGenerator(), functions1);
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
		Collection<String> partitions = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		int depth = 0;
		for (char c : content.toCharArray()) {
			if (';' == c && 0 == depth) {
				partitions.add(builder.toString());
				builder = new StringBuilder();
			} else {
				if ('{' == c) depth++;
				if ('}' == c) depth--;
				builder.append(c);
			}
		}
		partitions.add(builder.toString());
		String compileString = partitions.stream()
				.filter(partition -> !partition.isBlank())
				.map(compiler::parseSingle)
				.map(Node::render)
				.collect(Collectors.joining());
		String functionString = functions1.stream()
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
