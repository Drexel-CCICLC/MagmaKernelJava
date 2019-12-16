package com.meti;

import java.util.Collection;
import java.util.Collections;

class DeclareNode extends AbstractInheritedNode implements MutableNode, NamedNode {
	private final Collection<Keyword> keywords;
	private final boolean mutable;
	private final String name;

	public DeclareNode(Struct struct, String name) {
		this(struct, null, Collections.emptySet(), false, name);
	}

	public DeclareNode(Struct struct, Node value, Collection<Keyword> keywords, boolean mutable, String name) {
		super(struct, value);
		this.keywords = keywords;
		this.mutable = mutable;
		this.name = name;
	}

	public DeclareNode(Node node, boolean mutable, String name, Collection<Keyword> keywords) {
		super(node);
		this.mutable = mutable;
		this.name = name;
		this.keywords = keywords;
	}

	@Override
	public String compile(Aliaser aliaser) {
		return keywords.contains(Keyword.NATIVE) ? "" :
				"var " + aliaser.alias(name) + "=" + value.compile(aliaser) + ";";
	}

	@Override
	public Node transform() {
		return this;
	}

	@Override
	public boolean isMutable() {
		return mutable;
	}

	@Override
	public String name() {
		return name;
	}
}
