package com.meti.interpret;

import com.meti.compile.Compiler;
import com.meti.declare.Declarations;
import com.meti.declare.TreeDeclarations;
import com.meti.node.Node;
import com.meti.node.Parser;
import com.meti.node.Resolver;
import com.meti.node.UnitCompiler;
import com.meti.node.bracket.struct.IncrementedGenerator;
import com.meti.node.value.primitive.array.Cache;
import com.meti.node.value.primitive.array.ListedCache;
import com.meti.util.BracketPartitioner;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class MagmaInterpreter implements Interpreter {
	private final Declarations declarations = new TreeDeclarations();
	private final Cache cache = new ListedCache();
	private final Collection<String> headers;
	private final Interpreter parent = new CInterpreter();
	private final Parser rootParser = new MagmaParser(declarations, new IncrementedGenerator(), cache);
	private final Resolver rootResolver = new MagmaResolver(declarations);
	private final Compiler compiler = new UnitCompiler(rootParser, rootResolver);

	public MagmaInterpreter(Collection<String> headers) {
		this.headers = headers;
	}

	@Override
	public Optional<String> parse(String content) {
		Collection<String> partitions = partitionContent(content);
		String output = buildOutput(partitions);
		return Optional.of(output);
	}

	private String buildOutput(Collection<String> partitions) {
		String compileString = compilePartitions(partitions);
		String headerString = joinHeaders();
		String functionString = joinFunctions();
		return headerString + functionString + compileString;
	}

	private String joinFunctions() {
		return cache.stream()
				.map(Node::render)
				.collect(Collectors.joining());
	}

	private String joinHeaders() {
		return headers.stream()
				.map(header -> "#include <" + header + ">\n")
				.collect(Collectors.joining());
	}

	private Collection<String> partitionContent(String content) {
		return BracketPartitioner.create().partition(content);
	}

	private String compilePartitions(Collection<String> partitions) {
		return partitions.stream()
				.filter(partition -> !partition.isBlank())
				.map(compiler::parseSingle)
				.map(Node::render)
				.collect(Collectors.joining());
	}

	@Override
	public String run(String content) throws IOException, InterruptedException {
		String code = parse(content).orElseThrow();
		return parent.run(code);
	}
}
