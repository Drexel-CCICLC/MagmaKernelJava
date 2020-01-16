package com.meti;

import java.util.List;

class InterpretedTest {
	protected final Interpreter interpreter = new MagmaInterpreter(List.of(
			"stdio.h",
			"stdlib.h"
	));
}
