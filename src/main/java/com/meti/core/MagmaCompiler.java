package com.meti.core;

import com.meti.Cache;
import com.meti.Parser;
import com.meti.Resolver;
import com.meti.Unit;
import com.meti.node.declare.Declarations;
import com.meti.node.declare.DeclareParser;
import com.meti.node.declare.TreeDeclarations;
import com.meti.node.struct.StructUnit;

public class MagmaCompiler extends UnitCompiler {
	public MagmaCompiler(Cache cache) {
		this(cache, new TreeDeclarations());
	}

	public MagmaCompiler(Cache cache, Declarations declarations) {
		this(cache, declarations, new StructUnit(declarations, cache));
	}

	public MagmaCompiler(Cache cache, Declarations declarations, Unit unit) {
		this(new ParentParser(
						unit,
						new DeclareParser(declarations)
				),
				new ParentResolver(
						unit
						/*new StringResolver(),*/
				));
	}

	private MagmaCompiler(Parser rootParser, Resolver rootResolver) {
		super(rootParser, rootResolver);
	}
}
