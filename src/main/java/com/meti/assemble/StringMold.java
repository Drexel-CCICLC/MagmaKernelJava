package com.meti.assemble;

import com.meti.lex.Token;
import com.meti.lex.TokenType;

import java.util.Optional;
import java.util.function.Function;

class StringMold implements Mold {
    private Token<?> token;

    @Override
    public void pour(Token<?> token) {
        if(token.type().equals(TokenType.STRING)) {
            this.token = token;
        }
    }

    @Override
    public Optional<Node> set(Assembler assembler) {
        return Optional.ofNullable(token)
                .map((Function<Token<?>, String>) Token::castedValue)
                .map(StringNode::new);
    }
}
