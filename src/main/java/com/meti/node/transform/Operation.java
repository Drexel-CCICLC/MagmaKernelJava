package com.meti.node.transform;

import com.meti.node.Node;
import com.meti.node.Type;

import java.util.function.Function;

interface Operation {
    boolean isPresent(String content);

    String render(Node node0, Node node1);

    Node toNode(String content, Function<? super String, ? extends Node> parser);

    Type toType(String content, Function<? super String, ? extends Type> applicator);
}
