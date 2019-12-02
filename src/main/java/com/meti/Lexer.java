package com.meti;

import java.util.List;

public interface Lexer {
	List<Token<?>> parse(String value);
}
