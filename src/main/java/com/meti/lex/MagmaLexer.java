package com.meti.lex;

import java.util.List;

public class MagmaLexer extends TokenizerLexer {
	public MagmaLexer() {
        this(List.of(
				new DeclareTokenizer(),
				new IntegerTokenizer(),
				new EndTokenizer(),
				new ContentTokenizer()
		));
	}

	public MagmaLexer(List<? extends Tokenizer> tokenizers) {
		super(tokenizers);
	}
}