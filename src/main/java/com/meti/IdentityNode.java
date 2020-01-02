package com.meti;

import java.util.Optional;

class IdentityNode implements Node {
	private final String value;

	public IdentityNode(String value) {
		this.value = value;
	}

	@Override
	public Optional<Declaration> declaration() {
		return Optional.empty();
	}

	@Override
	public String render() {
		return value;
	}
}
