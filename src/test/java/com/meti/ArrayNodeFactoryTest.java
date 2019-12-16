package com.meti;

import org.junit.jupiter.api.Test;

import static com.meti.PrimitiveNodeFactory.PrimitiveStruct.INT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayNodeFactoryTest {

	@Test
	void parse() {
		Node node = new ArrayNodeFactory()
				.parse("[3,2,1]", new FactoryParser(new PrimitiveNodeFactory()), null)
				.orElseThrow();
		String actual = node.compile(new IncrementAliaser(), new ListNodeTree());
		assertEquals("[3,2,1]", actual);
		Struct struct = node.struct();
		assertTrue(struct instanceof GenericStruct);
		assertEquals(INT, ((GenericStruct) struct).generics().get("T"));
	}
}