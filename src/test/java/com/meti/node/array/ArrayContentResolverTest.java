package com.meti.node.array;

import com.meti.Compiler;
import com.meti.core.ParentResolver;
import com.meti.node.Type;
import com.meti.core.UnitCompiler;
import com.meti.node.primitive.IntResolver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayContentResolverTest {

	@Test
	void resolveValue() {
		Compiler compiler = new UnitCompiler(null, new ParentResolver(
				new ArrayContentResolver(),
                new IntResolver()
		));
		Type type = compiler.resolveValue("Array<Int>{3, 4");
		assertEquals("int array[]", type.render("array"));
	}
}