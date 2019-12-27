package com.meti;

import com.meti.type.Type;
import com.meti.unit.Declaration;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.meti.type.PrimitiveType.ANY;
import static org.junit.jupiter.api.Assertions.*;

class TreeDeclarationsTest {

	@Test
	void absolute() {
		Declarations declarations = new TreeDeclarations();
		declarations.define("some0", ANY);
		declarations.sink("some0");
		declarations.define("some1", ANY);
		declarations.sink("some1");
		declarations.define("some2", ANY);
		declarations.surface();
		declarations.surface();
		Declaration declaration = declarations.absolute(List.of("some0", "some1", "some2"));
		assertEquals(ANY, declaration.type());
	}

	@Test
	void define() {
		Map<String, Declaration> children = new HashMap<>();
		Declaration root = new TestDeclaration(children);
		Declarations declarations = new TreeDeclarations(root);
		Declaration expected = declarations.define("test", ANY);
		assertTrue(children.containsKey("test"));
		assertEquals(children.get("test"), expected);
	}

	@Test
	void defineChild() {
		Map<String, Declaration> children = new HashMap<>();
		Declaration root = new TestDeclaration(children);
		Declarations declarations = new TreeDeclarations(root);
		declarations.define("some", ANY);
		declarations.sink("some");
		declarations.define("test", ANY);
		declarations.surface();
		assertTrue(children.containsKey("some"));
		Declaration declaration = children.get("some");
		assertTrue(declaration.child("test").isPresent());
	}

	@Test
	void delete() {
		Map<String, Declaration> children = new HashMap<>();
		Declaration root = new MutableTreeDeclaration(children, "root");
		Declarations declarations = new TreeDeclarations(root);
		declarations.define("test", ANY);
		declarations.delete("test");
		assertTrue(children.isEmpty());
	}

	@Test
	void relative() {
		Declarations declarations = new TreeDeclarations();
		Declaration expected = declarations.define("a", ANY);
		declarations.define("b", ANY);
		declarations.sink("b");
		Declaration actual = declarations.relative("a");
		assertSame(actual, expected);
		declarations.surface();
	}

	@Test
	void sink() {
		Declarations manager = new TreeDeclarations();
		manager.sink("test");
		assertDoesNotThrow(manager::surface);
	}

	@Test
	void surface() {
		Declarations manager = new TreeDeclarations();
		assertThrows(EmptyStackException.class, manager::surface);
	}

	private static final class TestDeclaration implements Declaration {
		private final Map<String, Declaration> children;

		private TestDeclaration(Map<String, Declaration> children) {
			this.children = children;
		}

		@Override
		public Optional<Declaration> child(String name) {
			return Optional.ofNullable(children.get(name));
		}

		@Override
		public Map<String, Declaration> children() {
			return children;
		}

		@Override
		public Declaration define(String name, Type type, Collection<String> flags) {
			Declaration child = new TestDeclaration(new HashMap<>());
			children.put(name, child);
			return child;
		}

		@Override
		public void delete(String name) {

		}

		@Override
		public Collection<String> flags() {
			return Collections.emptySet();
		}

		@Override
		public String name() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Type type() {
			return ANY;
		}

		@Override
		public boolean hasFlag(String value) {
			return false;
		}

		@Override
		public boolean isMutable() {
			return false;
		}

		@Override
		public boolean isNative() {
			return false;
		}

		@Override
		public OptionalInt order(String child) {
			return OptionalInt.empty();
		}
	}
}