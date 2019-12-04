package com.meti.assemble;

public interface Node {
    boolean hasProperty(NodeProperty key);

    <T> T getProperty(NodeProperty key);
}
