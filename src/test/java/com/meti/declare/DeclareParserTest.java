package com.meti.declare;

import com.meti.Compiler;
import com.meti.*;
import com.meti.array.ListedFunctions;
import com.meti.other.AnyResolver;
import com.meti.other.VoidResolver;
import com.meti.string.StringResolver;
import com.meti.struct.IncrementedGenerator;
import com.meti.struct.StructParser;
import com.meti.struct.StructResolver;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

class DeclareParserTest {
	private final Declarations declarations = new Declarations();

	@Test
	void bug0() {
		Compiler compiler = new UnitCompiler(new ParentParser(new StructParser(declarations, new ListedFunctions(), new IncrementedGenerator())),
                new ParentResolver(
				new StructResolver(),
				new StringResolver(),
				new AnyResolver(),
				new VoidResolver()));
		Parser parser = new DeclareParser(declarations);
		Collection<Node> collection = parser.parseMultiple("native val printf = (String format, Any value) => Void",
                compiler);
		assertFalse(collection.isEmpty());
		assertSame(EmptyNode.class, collection.toArray()[0].getClass());
	}
}