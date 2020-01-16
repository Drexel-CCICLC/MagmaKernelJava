package com.meti;

import java.util.List;

class InterpretedTest {
	final Interpreter interpreter = new MagmaInterpreter(List.of(
			"stdio.h",
			"stdlib.h"
	));
}
