package com.meti.assemble;

import java.util.Map;

public class StringNode extends MapNode {
    StringNode(String value) {
        super(Map.of(NodeProperty.VALUE, value));
    }
}
