package com.meti.assemble;

import java.util.Map;

import static com.meti.assemble.NodeProperty.NAME;
import static com.meti.assemble.NodeProperty.VALUE;

public class AssignNode extends MapNode {
    public static final String ID = "assign";

    public AssignNode(String name, Node value) {
        super(Map.of(
				NodeProperty.ID, ID,
                VALUE, value,
                NAME, name
        ));
    }
}
