package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvocationNodeFactoryTest {

	@Test
	void parse() {
		NodeTree tree = new ListNodeTree();
		Parser parser = new FactoryParser(
				new BlockNodeFactory(),
				new DeclareNodeFactory(tree),
				new StructureNodeFactory(tree),
				new ReturnNodeFactory(),
				new InvocationNodeFactory(tree),
				new PrimitiveNodeFactory(),
				new VariableNodeFactory(tree)
		);
		var result = parser.parse("{val identity=(x int)=>int:{return x;};val result=identity(10);}")
				.compile(new IncrementAliaser());
		assertEquals("{var a0=function(b1){return b1;};var c2=a0(10);}", result);
	}
}