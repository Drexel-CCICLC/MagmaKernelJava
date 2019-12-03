package com.meti.lex;

import java.util.Set;

public class MagmaLexer extends TokenizerLexer {
    public MagmaLexer() {
        this(Set.of(
                new ContentTokenizer(),
                new DeclareTokenizer()
        ));
    }

    public MagmaLexer(Set<? extends Tokenizer> tokenizers) {
        super(tokenizers);
    }
}
