package com.meti.node.value.compound.variable;

import com.meti.compile.Compiler;
import com.meti.declare.Declarations;
import com.meti.declare.TreeDeclarations;
import com.meti.node.Node;
import com.meti.node.Parser;
import com.meti.node.UnitCompiler;
import com.meti.node.bracket.struct.ObjectType;
import com.meti.node.value.primitive.integer.IntType;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static com.meti.node.bracket.struct.StructTypeBuilder.create;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VariableParserTest {
	@Test
	void field() {
		Declarations declarations = new TreeDeclarations();
		declarations.define("Point", create().build(), () -> {
			declarations.define("x", IntType.INSTANCE, Collections.emptySet());
		});
		declarations.define("a", new ObjectType(declarations, "Point"), Collections.emptySet());
		Compiler compiler = new UnitCompiler(new VariableParser(declarations), new VariableResolver(declarations));
		Node node = compiler.parseSingle("a.x");
		assertEquals("*(int*)a[0]", node.render());
	}

	@Test
	void simple() {
		Declarations declarations = new TreeDeclarations();
		declarations.define("test", IntType.INSTANCE, Collections.emptySet());
		Parser parser = new VariableParser(declarations);
		Optional<Node> node = parser.parse("test", null);
		assertTrue(node.isPresent());
		assertEquals("test", node.get().render());
	}
}