package com.meti.lex;

import java.util.List;

public interface Lexer {
	List<Token<?>> parse(String value);
}
