package com.meti.node.bracket.block;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Parser;
import com.meti.util.BracketPartitioner;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlockParser implements Parser {

	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		return Optional.of(value)
				.map(String::trim)
				.filter(trim -> trim.startsWith("{") && trim.endsWith("}"))
				.map(trim -> build(trim, compiler));
	}

	private Node build(String trim, Compiler compiler) {
		String childrenString = trim.substring(1, trim.length() - 1);
		Collection<String> partitions = partition(childrenString);
		List<Node> children = compilePartitions(compiler, partitions);
		return new BlockNode(children);
	}

	private Collection<String> partition(String childrenString) {
		return BracketPartitioner.create().partition(childrenString);
	}

	private List<Node> compilePartitions(Compiler compiler, Collection<String> partitions) {
		return partitions.stream()
				.map(String::trim)
				.filter(childString -> !childString.isEmpty())
				.map(compiler::parseSingle)
				.collect(Collectors.toList());
	}
}
