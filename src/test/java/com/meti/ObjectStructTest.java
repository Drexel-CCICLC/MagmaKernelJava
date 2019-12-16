package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectStructTest {

	@Test
	void isInstance() {
		Struct parent = new ObjectStruct("test");
		Struct child = new ObjectStruct("test1", parent);
		assertTrue(child.isInstance(parent));
		assertFalse(parent.isInstance(child));
	}

	@Test
	void merge(){
		Struct root = new ObjectStruct("vehicle");
		Struct car = new ObjectStruct("car", root);
		Struct boat = new ObjectStruct("boat", root);
		Struct merge = car.merge(boat);
		assertEquals(root, merge);
	}
}