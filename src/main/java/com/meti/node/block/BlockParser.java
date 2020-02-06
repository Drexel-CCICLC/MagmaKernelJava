package com.meti.node.block;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.core.task.BracketPartitioner;
import com.meti.core.task.Partitioner;
import com.meti.node.Node;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlockParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		String trim = content.trim();
		if (trim.startsWith("{") && trim.endsWith("}")) {
			String childString = trim.substring(1, trim.length() - 1);
			Partitioner partitioner = new BracketPartitioner(childString);
			List<Node> children = partitioner.partition()
					.stream()
					.filter(s -> !s.isBlank())
					.map(compiler::parse)
					.collect(Collectors.toList());
			return Optional.of(new BlockNode(children));
		}
		return Optional.empty();
	}
}
