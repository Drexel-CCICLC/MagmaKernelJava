package com.meti.assemble;

import com.meti.lex.Token;
import com.meti.lex.TokenType;

import java.util.Optional;
import java.util.function.Function;

class IntegerMold implements NodeMold {
    private Token<?> value;

    @Override
    public void pour(Token<?> token) {
        if(token.type() == TokenType.INTEGER) this.value = token;
    }

    @Override
    public Optional<Node> set(Assembler assembler) {
        return Optional.ofNullable(value)
                .map((Function<Token<?>, Integer>) Token::castedValue)
                .map(IntegerNode::new);
    }
}

