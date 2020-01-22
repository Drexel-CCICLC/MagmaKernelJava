package com.meti.node.bracket.declare;

import com.meti.compile.Compiler;
import com.meti.declare.*;
import com.meti.node.EmptyNode;
import com.meti.node.Node;
import com.meti.node.Parser;
import com.meti.node.UnitCompiler;
import com.meti.node.value.primitive.array.ListedCache;
import com.meti.interpret.ParentParser;
import com.meti.interpret.ParentResolver;
import com.meti.node.other.AnyResolver;
import com.meti.node.other.VoidResolver;
import com.meti.node.value.primitive.string.StringResolver;
import com.meti.node.bracket.struct.IncrementedGenerator;
import com.meti.node.bracket.struct.StructParser;
import com.meti.node.bracket.struct.StructResolver;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

class DeclareParserTest {
	private final Declarations declarations = new TreeDeclarations();

	@Test
	void bug0() {
		Compiler compiler = new UnitCompiler(new ParentParser(new StructParser(declarations, new ListedCache(), new IncrementedGenerator())),
                new ParentResolver(
				new StructResolver(),
				new StringResolver(),
				new AnyResolver(),
				new VoidResolver()));
		Parser parser = new DeclareParser(declarations);
		Optional<Node> collection = parser.parse("native val printf = (String format, Any value) => Void",
                compiler);
		assertFalse(collection.isEmpty());
		assertSame(EmptyNode.class, collection.get().getClass());
	}
}