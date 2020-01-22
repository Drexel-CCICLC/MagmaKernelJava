package com.meti.node.bracket.struct;

import com.meti.node.Node;

public interface GeneratedNodeBuilder {
    Node create(Generator generator);

    GeneratedNodeBuilder withName(String name);
}
