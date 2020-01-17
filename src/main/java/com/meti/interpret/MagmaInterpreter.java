package com.meti.interpret;

import com.meti.compile.Compiler;
import com.meti.declare.*;
import com.meti.node.Node;
import com.meti.node.Parser;
import com.meti.node.Resolver;
import com.meti.node.UnitCompiler;
import com.meti.node.value.primitive.array.Functions;
import com.meti.node.value.primitive.array.ListedFunctions;
import com.meti.node.bracket.struct.IncrementedGenerator;
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

	public MagmaInterpreter(Collection<String> headers) {
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
