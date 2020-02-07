package com.meti.node.block;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;
import com.meti.util.BracketPartitioner;
import com.meti.util.Partitioner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlockParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		return Optional.of(content)
				.map(String::trim)
				.filter(s -> s.startsWith("{") && s.endsWith("}"))
				.map(s -> s.substring(1, s.length() - 1))
				.map(s -> parseChildren(compiler, s))
				.map(BlockNode::new);
	}

	private List<Node> parseChildren(Compiler compiler, String childString) {
		Partitioner partitioner = new BracketPartitioner(childString);
		return partitioner.partition()
				.stream()
				.filter(s -> !s.isBlank())
				.map(compiler::parse)
				.collect(Collectors.toList());
	}
}
