package com.meti.declare;

import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.value.compound.variable.FieldNodeBuilder;
import com.meti.node.value.primitive.integer.IntType;
import com.meti.util.Binding;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.meti.declare.TreeDeclarationBuilder.create;
import static com.meti.node.other.AnyType.INSTANCE;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

class TreeDeclarationTest {

	@Test
	void ancestors() {
		Declarations declarations = new TreeDeclarations();
		Declaration child = declarations.define("grandparent", INSTANCE,
				() -> declarations.define("parent", INSTANCE,
						() -> declarations.define("child", INSTANCE, Collections.emptySet())));
		Declaration parent = declarations.absolute(List.of("grandparent", "parent"));
		Declaration grandparent = declarations.absolute(singleton("grandparent"));
		List<Declaration> ancestors = child.ancestors();
		assertEquals(2, ancestors.size());
		assertSame(grandparent, ancestors.get(0));
		assertSame(parent, ancestors.get(1));
	}

	@Test
	void buildAssignments() {

	}

	@Test
	void child() {
		Declaration child = create()
				.withStack(singletonList("child"))
				.build();
		Declaration parent = create()
				.withChildren(singletonList(child))
				.build();
		Optional<Declaration> result = parent.child("child");
		assertTrue(result.isPresent());
		assertSame(child, result.get());
	}

	@Test
	void declareInstance() {
	}

	@Test
	void define() {
		List<Declaration> children = new ArrayList<>();
		Declaration root = create()
				.withChildren(children)
				.build();
		Declaration child = root.define("test", IntType.INSTANCE, Collections.emptySet());
		assertEquals(1, children.size());
		assertTrue(children.contains(child));
	}

	@Test
	void defineParameter() {
		List<Declaration> children = new ArrayList<>();
		Declaration root = create()
				.withChildren(children)
				.build();
		Declaration child = root.define(Parameter.of("test", IntType.INSTANCE));
		assertEquals(1, children.size());
		assertTrue(children.contains(child));
	}

	@Test
	void hasChildAsParameter() {
		Declaration root = create().build();
		Declaration child = root.define(Parameter.of("test", IntType.INSTANCE));
		assertTrue(child.isParameter());
	}

	@Test
	void isParameter() {
		assertTrue(create()
				.withParameter(true)
				.build()
				.isParameter());
	}

	@Test
	void lookupFieldOrder() {
		Declaration parent = create().build();
		parent.define("var0", IntType.INSTANCE, Collections.emptySet());
		parent.define("var1", IntType.INSTANCE, Collections.emptySet());
		Binding<Integer> order = Binding.empty();
		FieldNodeBuilder expected = new TestFieldNodeBuilder(order, Binding.empty());
		FieldNodeBuilder actual = parent.lookupFieldOrder("var1", expected);
		assertSame(expected, actual);
		assertEquals(1, order.get());
	}

	@Test
	void lookupFieldType() {
		Declaration parent = create().build();
		parent.define("var0", INSTANCE, Collections.emptySet());
		parent.define("var1", IntType.INSTANCE, Collections.emptySet());
		Binding<Type> type = Binding.empty();
		FieldNodeBuilder expected = new TestFieldNodeBuilder(Binding.empty(), type);
		FieldNodeBuilder actual = parent.lookupFieldType("var1", expected);
		assertSame(expected, actual);
		assertEquals(IntType.INSTANCE, type.get());
	}

	@Test
	void matches() {
		assertTrue(create()
				.withStack(singletonList("test"))
				.build()
				.matches("test"));
	}

	@Test
	void parent() {
	}

	@Test
	void testDefine() {
	}

	@Test
	void toInstance() {
	}

	@Test
	void toInstancePair() {
	}

	@Test
	void toParameter() {
	}

	@Test
	void toStruct() {
	}

	@Test
	void type() {
	}

	private static final class TestFieldNodeBuilder implements FieldNodeBuilder {
		private final Binding<? super Integer> order;
		private final Binding<? super Type> type;

		private TestFieldNodeBuilder(Binding<? super Integer> order, Binding<? super Type> type) {
			this.order = order;
			this.type = type;
		}

		@Override
		public Node build() {
			return null;
		}

		@Override
		public FieldNodeBuilder withInstanceArray(Node instanceArray) {
			return null;
		}

		@Override
		public FieldNodeBuilder withName(String name) {
			return null;
		}

		@Override
		public FieldNodeBuilder withOrder(int order) {
			this.order.set(order);
			return this;
		}

		@Override
		public FieldNodeBuilder withParent(Declaration parent) {
			return null;
		}

		@Override
		public FieldNodeBuilder withType(Type type) {
			this.type.set(type);
			return this;
		}
	}
}