package com.meti;

import com.meti.core.CollectionCache;
import com.meti.node.Node;
import com.meti.node.Parameter;
import com.meti.node.declare.VariableNode;
import com.meti.node.primitive.IntNode;
import com.meti.node.primitive.IntType;
import com.meti.node.struct.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class CacheTest {

	@Test
	void add() {
		List<Node> main = new ArrayList<>();
		Cache cache = new CollectionCache(Collections.emptyList(), Collections.emptyList(), main);
		Node expected = new IntNode(10);
		cache.add(expected);
		assertEquals(expected, main.get(0));
	}

	@Test
	void addFunction() {
		Collection<Node> nodes = new ArrayList<>();
		Cache cache = new CollectionCache(Collections.emptyList(), nodes, new ArrayList<>());
		Node function = new IntNode(10);
		cache.addFunction(function);
		assertIterableEquals(Collections.singleton(function), nodes);
	}

	@Test
	void addStruct() {
		Collection<Node> nodes = new ArrayList<>();
		Cache cache = new CollectionCache(nodes, Collections.emptyList(), new ArrayList<>());
		Node struct = new IntNode(10);
		cache.addStruct(struct);
		assertIterableEquals(Collections.singleton(struct), nodes);
	}

	@Test
	void render() {
		Cache cache = new CollectionCache();
		Collection<Parameter> structParams = List.of(
				Parameter.create(IntType.INSTANCE, Collections.singletonList("x")),
				Parameter.create(IntType.INSTANCE, Collections.singletonList("y")));
		cache.addStruct(new StructNode("Point", structParams));

		Collection<Parameter> getXParams = Collections.singleton(Parameter.create(new StructType("Point"),
				Collections.singletonList("Point_")));
		Collection<Node> getXContent = Collections.singleton(new ReturnNode(new FieldNode(new VariableNode("Point_"),
				"x")));
		cache.addFunction(new FunctionNode("Point_getX", IntType.INSTANCE, getXParams, new BlockNode(getXContent)));

		Collection<Parameter> getYParams = Collections.singleton(Parameter.create(new StructType("Point"),
				Collections.singletonList("Point_")));
		Collection<Node> getYContent = Collections.singleton(new ReturnNode(new FieldNode(new VariableNode("Point_"),
				"y")));
		cache.addFunction(new FunctionNode("Point_getY", IntType.INSTANCE, getYParams, new BlockNode(getYContent)));

		assertEquals("int _exitCode=0;" +
				"void *_throw=NULL;" +
				"struct Point{int x;int y;};" +
				"int Point_getX(struct Point Point_){" +
				"return Point_.x;}" +
				"int Point_getY(struct Point Point_){" +
				"return Point_.y;}" +
				"int main(){" +
				"return _exitCode;}", cache.render());
	}
}