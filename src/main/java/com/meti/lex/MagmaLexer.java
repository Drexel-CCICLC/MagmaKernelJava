package com.meti.lex;

import java.util.Set;

public class MagmaLexer extends TokenizerLexer {
	public MagmaLexer() {
        this(Set.of(
				new DeclareTokenizer(),
				new IntegerTokenizer(),
				new EndTokenizer(),
				new ContentTokenizer()
		));
	}

	public MagmaLexer(Set<? extends Tokenizer> tokenizers) {
		super(tokenizers);
	}
}
