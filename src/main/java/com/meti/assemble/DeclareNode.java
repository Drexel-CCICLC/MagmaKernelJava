package com.meti.assemble;

import java.util.Map;

import static com.meti.assemble.NodeProperty.*;

public class DeclareNode extends MapNode {
	public static final String ID = "declare";

	public DeclareNode(boolean isMutable, String name, Node value) {
		super(Map.of(
				NodeProperty.ID, ID,
				MUTABLE, isMutable,
				VALUE, value,
				NAME, name
		));
	}
}
