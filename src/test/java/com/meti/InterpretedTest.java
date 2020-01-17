package com.meti;

import com.meti.interpret.Interpreter;
import com.meti.interpret.MagmaInterpreter;

import java.util.List;

class InterpretedTest {
	final Interpreter interpreter = new MagmaInterpreter(List.of(
			"stdio.h",
			"stdlib.h"
	));
}
