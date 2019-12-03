package com.meti.assemble;

import java.util.HashMap;
import java.util.Map;

class MapNode implements Node {
    private final Map<NodeProperty, Object> map = new HashMap<>();

    public MapNode(Map<NodeProperty, Object> others) {
        map.putAll(others);
    }

    @Override
    public <T> T getProperty(NodeProperty key) {
        return (T) map.get(key);
    }
}
