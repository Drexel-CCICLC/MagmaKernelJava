package com.meti;

import com.meti.primitive.IntNode;
import com.meti.primitive.IntType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
		Cache cache = new Cache(Collections.emptyList(), Collections.emptyList(), main);
		Node expected = new IntNode(10);
		cache.add(expected);
		assertEquals(expected, main.get(1));
	}

	@Test
	void addFunction() {
		Collection<Node> nodes = new ArrayList<>();
		Cache cache = new Cache(Collections.emptyList(), nodes, new ArrayList<>());
		Node function = Mockito.mock(Node.class);
		cache.addFunction(function);
		assertIterableEquals(Collections.singleton(function), nodes);
	}

	@Test
	void addStruct() {
		Collection<Node> nodes = new ArrayList<>();
		Cache cache = new Cache(nodes, Collections.emptyList(), new ArrayList<>());
		Node struct = Mockito.mock(Node.class);
		cache.addStruct(struct);
		assertIterableEquals(Collections.singleton(struct), nodes);
	}

	@Test
	void render() {
		Cache cache = new Cache();
		Collection<Parameter> structParams = List.of(
				new Parameter(IntType.INSTANCE, "x"),
				new Parameter(IntType.INSTANCE, "y"));
		cache.addStruct(new StructNode("Point", structParams));

		Collection<Parameter> getXParams = Collections.singleton(new Parameter(new StructType("Point"), "Point_"));
		Collection<Node> getXContent = Collections.singleton(new ReturnNode(new FieldNode(new VariableNode("Point_"),
				"x")));
		cache.addFunction(new FunctionNode("Point_getX", IntType.INSTANCE, getXParams, new BlockNode(getXContent)));

		Collection<Parameter> getYParams = Collections.singleton(new Parameter(new StructType("Point"), "Point_"));
		Collection<Node> getYContent = Collections.singleton(new ReturnNode(new FieldNode(new VariableNode("Point_"),
				"y")));
		cache.addFunction(new FunctionNode("Point_getY", IntType.INSTANCE, getYParams, new BlockNode(getYContent)));

		assertEquals("int _exit=0i;" +
				"struct Point{int x;int y;};" +
				"int Point_getX(struct Point Point_){return Point_.x;}" +
				"int Point_getY(struct Point Point_){return Point_.y;}" +
				"int main(){return _exit;}", cache.render());
	}
}