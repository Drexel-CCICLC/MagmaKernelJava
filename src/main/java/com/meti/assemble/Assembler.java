package com.meti.assemble;

import com.meti.lex.Token;

import java.util.List;

interface Assembler {
    List<Node> assemble(List<Token<?>> tokens);
}
