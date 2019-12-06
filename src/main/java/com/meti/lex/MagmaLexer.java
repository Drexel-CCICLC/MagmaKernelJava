package com.meti.lex;

import java.util.List;

public class MagmaLexer extends TokenizerLexer {
	public MagmaLexer() {
		this(new Binding<>(0));
	}

	public MagmaLexer(Binding<Integer> depth) {
        this(List.of(
				new DeclareTokenizer(),
				new IntegerTokenizer(),
				new StringTokenizer(),
				new EndTokenizer(depth),
		        new KeywordTokenizer(),
				new BracketTokenizer(depth),
				new ParenthesisTokenizer(),
				new ContentTokenizer()
		));
	}

	private MagmaLexer(List<? extends Tokenizer> tokenizers) {
		super(tokenizers);
	}
}
