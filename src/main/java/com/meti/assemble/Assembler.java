package com.meti.assemble;

import com.meti.lex.Binding;
import com.meti.lex.Token;

import java.util.Optional;
import java.util.stream.Stream;

public interface Assembler {
	Stream<Node> assemble(Stream<? extends Token<?>> tokens);

	Optional<Node> assembleSingle(Stream<? extends Token<?>> tokens);

	Binding<Integer> depth();
}
