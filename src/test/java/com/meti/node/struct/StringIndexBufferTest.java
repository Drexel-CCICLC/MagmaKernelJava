package com.meti.node.struct;

import com.meti.util.IndexBuffer;
import com.meti.util.StringIndexBuffer;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StringIndexBufferTest {

	@Test
	void cut() {
		IndexBuffer buffer = new StringIndexBuffer("01234", "2", "4");
		assertEquals("23", buffer.cut(0));
	}

	@Test
	void cutIfPresent() {
		IndexBuffer buffer = new StringIndexBuffer("(Int value) => Int : {return value;}", "(", "=>");
		Optional<String> result = buffer.cutIfPresent(0);
		assertTrue(result.isPresent());
		assertEquals("(Int value) ", result.get());
	}

	@Test
	void cutIfPresentAtEnd() {
		IndexBuffer buffer = new StringIndexBuffer("(Int value) => Int : {return value;}", ":");
		Optional<String> result = buffer.cutIfPresent(0);
		assertTrue(result.isPresent());
		assertEquals(": {return value;}", result.get());
	}

	@Test
	void cutMultiple() {
		IndexBuffer buffer = new StringIndexBuffer("(Int x, Int y) :{\n" +
		                                           "	val getX ==> Int :{\n" +
		                                           "		return x;\n" +
		                                           "	};\n" +
		                                           "	val getY ==> Int :{\n" +
		                                           "		return y;\n" +
		                                           "	};\n" +
		                                           "}\n", "(", "=>", ":");
		String result = buffer.cut(0);
		assertEquals("(Int x, Int y) ", result);
	}

	@Test
	void isValid() {

	}

	@Test
	void test() {
		IndexBuffer buffer = new StringIndexBuffer("            (Int x, Int y) :{\n" +
		                                           "	val getX ==> Int :{\n" +
		                                           "		return x;\n" +
		                                           "	};\n" +
		                                           "	val getY ==> Int :{\n" +
		                                           "		return y;\n" +
		                                           "	};\n" +
		                                           "}\n",
				"(",
				"=>",
				":");
		assertFalse(buffer.isPresent(1));
	}

	@Test
	void testIsPresentInvalid() {
		IndexBuffer buffer = new StringIndexBuffer("01234", "5");
		assertFalse(buffer.isPresent(0));
	}

	@Test
	void testIsPresentValid() {
		IndexBuffer buffer = new StringIndexBuffer("01234", "0");
		assertTrue(buffer.isPresent(0));
	}
}