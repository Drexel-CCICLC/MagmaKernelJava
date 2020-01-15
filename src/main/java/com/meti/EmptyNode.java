package com.meti;

public class EmptyNode implements Node {
    @Override
    public String render() {
        return "";
    }

    @Override
    public boolean isParent() {
		return false;
    }
}
