package com.meti;

import java.util.Optional;

public interface Node {
	Optional<Declaration> declaration();

	String render();
}
