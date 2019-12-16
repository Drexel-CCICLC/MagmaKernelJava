package com.meti;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

	public DeclareNode(boolean mutable, String name, Collection<Keyword> keywords) {
		this.mutable = mutable;
		this.name = name;
		this.keywords = keywords;
	}

	@Override
	public String compile(Aliaser aliaser, NodeTree tree) {
		return keywords.contains(Keyword.NATIVE) ? "" :
				"var " + aliaser.alias(nameArray()) + "=" + value.compile(aliaser, tree) + ";";
}

	@Override
	public Node transform() {
		value.transform();
		return this;
	}

	public String[] nameArray() {
		List<String> nameArgs = new ArrayList<>();
		DeclareNode current = this;
		while (current != null) {
			nameArgs.add(current.name);

			var parentOptional = current.getParent();
			if (parentOptional.isEmpty()) break;
			BlockNode block = (BlockNode) parentOptional.get();
			var functionOptional = block.getParent();
			if (functionOptional.isEmpty()) break;
			current = (DeclareNode) functionOptional.get().getParent().orElse(null);
		}
		Collections.reverse(nameArgs);
		return nameArgs.toArray(String[]::new);
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
