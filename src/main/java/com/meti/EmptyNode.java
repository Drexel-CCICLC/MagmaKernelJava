package com.meti;

import java.util.LinkedList;

public class EmptyNode implements Node {
	@Override
	public LinkedList<Node> children() {
		return new LinkedList<>();
	}

	@Override
    public String render() {
        return "";
    }

    @Override
    public boolean isParent() {
		return false;
    }
}
