package com.meti.core;

import com.meti.Cache;
import com.meti.Parser;
import com.meti.Resolver;
import com.meti.Unit;
import com.meti.node.block.BlockParser;
import com.meti.node.block.ElseParser;
import com.meti.node.block.IfParser;
import com.meti.node.declare.*;
import com.meti.node.primitive.*;
import com.meti.node.struct.*;
import com.meti.node.transform.NotParser;
import com.meti.node.transform.OperationParser;

class MagmaCompiler extends UnitCompiler {
	MagmaCompiler(Cache cache) {
		this(cache, new TreeDeclarations());
	}

	private MagmaCompiler(Cache cache, Declarations declarations) {
		this(declarations, new StructUnit(declarations, cache));
	}

	private MagmaCompiler(Declarations declarations, Unit unit) {
		this(new ParentParser(
						unit,
						new NullParser(),
						new ReturnParser(),
						new DeclareParser(declarations),
						new NotParser(),
						new IfParser(),
						new ElseParser(),
						new BlockParser(),
						new BooleanParser(),
						new InvocationParser(declarations),
						new OperationParser(),
						new ThisParser(declarations),
						new IntParser(),
						new StringParser(),
						new VariableParser(declarations)
				),
				new ParentResolver(
						unit,
						new InvocationResolver(declarations),
						new StringResolver(),
						new FloatResolver(),
						new VariableResolver(declarations),
						new ObjectResolver(declarations)
				));
	}

	private MagmaCompiler(Parser rootParser, Resolver rootResolver) {
		super(rootParser, rootResolver);
	}
}
