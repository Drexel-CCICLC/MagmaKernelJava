package com.meti;

import com.meti.array.Functions;
import com.meti.array.ListedFunctions;
import com.meti.struct.IncrementedGenerator;
import com.meti.util.BracketPartitioner;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class MagmaInterpreter implements Interpreter {
	private final Declarations declarations = new TreeDeclarations();
	private final Functions functions1 = new ListedFunctions();
	private final Collection<String> headers;
	private final Interpreter parent = new CInterpreter();
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
		Collection<String> partitions = BracketPartitioner.create().partition(content);
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
