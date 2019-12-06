package com.meti.assemble;

import com.meti.lex.Binding;
import com.meti.lex.Token;
import com.meti.lex.TokenType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlockMold extends BucketMold {
	private final BucketManager<Token<?>> manager = new ListBucketManager<>(
			by(type(TokenType.BRACKET).and(valueEquals(true))),
			by(type(TokenType.BRACKET).and(valueEquals(false)).negate()),
			by(any())
	);

	@Override
	public void pour(Token<?> token) {
		manager.put(token);
	}

	@Override
	public Optional<Node> set(Assembler assembler) {
		if (manager.allPresent(0, 2)) {
			Binding<Integer> depth = assembler.depth();
			depth.set(depth.get() + 1);
			List<Node> nodes = assembler.assemble(manager.at(1)).collect(Collectors.toList());
			depth.set(depth.get() - 1);
			return Optional.of(new BlockNode(nodes));
		}
		return Optional.empty();
	}
}
