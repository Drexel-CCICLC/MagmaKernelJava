package com.meti;

import com.meti.array.*;
import com.meti.block.BlockParser;
import com.meti.character.CharParser;
import com.meti.declare.AssignParser;
import com.meti.declare.DeclareParser;
import com.meti.integer.IntParser;
import com.meti.invoke.InvocationParser;
import com.meti.operator.OperationParser;
import com.meti.point.DereferenceParser;
import com.meti.point.ReferenceParser;
import com.meti.string.StringParser;
import com.meti.struct.Generator;
import com.meti.struct.IncrementedGenerator;
import com.meti.struct.ReturnParser;
import com.meti.struct.StructParser;
import com.meti.variable.VariableParser;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class MagmaParser extends ParentParser {
	MagmaParser(Declarations declarations, List<Node> functions, Generator generator) {
		this(
				new ArrayDeleteParser(),
                new ArrayIndexParser(),
				new ReturnParser(),
				new BlockParser(),
				new ArrayContentParser(new Functions(functions), generator),
				new ArraySizeParser(),
				new CharParser(),
				new StructParser(declarations, functions, generator),
				new DeclareParser(declarations),
				new AssignParser(),
				new IntParser(),
				new InvocationParser(declarations),
				new OperationParser(),
				new DereferenceParser(),
				new ReferenceParser(),
				new StringParser(),
				new VariableParser(declarations)
		);
	}

	MagmaParser(Parser... children) {
		this(Arrays.asList(children));
	}

	MagmaParser(Collection<Parser> children) {
		super(children);
	}

}
