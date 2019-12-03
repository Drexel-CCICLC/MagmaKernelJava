package com.meti.assemble;

import java.util.Map;
import java.util.stream.Stream;

import static com.meti.assemble.NodeProperty.*;

class DeclareNode extends MapNode {
    public DeclareNode(boolean isMutable, String name, Stream<Node> values) {
        super(Map.of(
                MUTABLE, isMutable,
                NAME, name,
                CONTENT, values
        ));
    }
}
