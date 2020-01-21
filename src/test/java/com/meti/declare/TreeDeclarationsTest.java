package com.meti.declare;

import com.meti.node.value.primitive.integer.IntType;
import com.meti.util.Binding;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.meti.node.other.AnyType.INSTANCE;
import static org.junit.jupiter.api.Assertions.*;

class TreeDeclarationsTest {

	@Test
	void absolute() {
		Declarations declarations = new TreeDeclarations();
		Declaration expected = declarations.define("main", IntType.INSTANCE, Collections.emptySet());
		Declaration actual = declarations.absolute(Collections.singleton("main"));
		assertSame(expected, actual);
	}

	@Test
	void current() {
		Declarations declarations = new TreeDeclarations();
		declarations.isRoot(declarations.current());
	}

	@Test
	void define() {
		Stack<String> stack = new Stack<>();
		List<Declaration> children = new ArrayList<>();
		TreeDeclarationBuilder builder = TreeDeclarationBuilder.create()
				.withChildren(children)
				.withParameter(false)
				.withStack(stack)
				.withType(null);
		Declarations declarations = new TreeDeclarations(stack, builder);
		Declaration expected = declarations.define("test", IntType.INSTANCE, Collections.emptySet());
		assertEquals(1, children.size());
		assertEquals(expected, children.get(0));
	}

	@Test
	void defineWithRunnable() {
		Declarations declarations = new TreeDeclarations();
		Binding<Declaration> child = Binding.empty();
		declarations.define("parent", INSTANCE,
				() -> child.set(declarations.define("child", INSTANCE, Collections.emptySet())));
		Optional<Declaration> optional = declarations.absolute(Collections.singleton("parent")).child("child");
		assertTrue(optional.isPresent());
		assertSame(child.get(), optional.get());
	}

	@Test
	void defineWithSupplier() {
		Declarations declarations = new TreeDeclarations();
		Declaration child = declarations.define("parent", INSTANCE,
				() -> declarations.define("child", INSTANCE, Collections.emptySet()));
		Optional<Declaration> optional = declarations.absolute(Collections.singleton("parent")).child("child");
		assertTrue(optional.isPresent());
		assertSame(child, optional.get());
	}

	@Test
	void isCurrent() {
	}

	@Test
	void isRoot() {
	}

	@Test
	void parentOf() {
	}

	@Test
	void relative() {
		Declarations declarations = new TreeDeclarations();
		declarations.define("parent", INSTANCE, () -> {
			Declaration expected = declarations.define("child0", INSTANCE, Collections.emptySet());
			declarations.define("child1", INSTANCE, () -> {
				Optional<Declaration> optional = declarations.relative("child0");
				assertTrue(optional.isPresent());
				assertSame(expected, optional.get());
			});
		});
	}

	@Test
	void stream() {
	}

	@Test
	void testDefine2() {
	}
}