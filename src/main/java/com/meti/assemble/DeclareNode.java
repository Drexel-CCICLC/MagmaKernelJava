package com.meti.assemble;

import java.util.Map;

import static com.meti.assemble.NodeProperty.*;

public class DeclareNode extends MapNode {
    public DeclareNode(boolean isMutable, String name, Node value) {
        super(Map.of(
                MUTABLE, isMutable,
                NAME, name,
                VALUE, value
        ));
    }
}
