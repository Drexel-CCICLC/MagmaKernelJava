package com.meti;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DeclareNodeFactory implements NodeFactory {
	private final NodeTree tree;

	public DeclareNodeFactory(NodeTree tree) {
		this.tree = tree;
	}

	@Override
	public Optional<Node> parse(String value, Parser parser) {
		BucketManager manager = new QueueBucketManager(
				Bucket.build().exclude('='),
				Bucket.build().include('=').restrict(1),
				Bucket.build()).pour(value);
		if (!manager.isValid()) return Optional.empty();
		String keywordString = manager.next();
		String equals = manager.next();
		String content = manager.next();
		List<String> s1 = List.of(keywordString.split(" "));
		List<String> withoutName = s1.subList(0, s1.size() - 1);
		Collection<Keyword> keywords = new ArrayList<>();
		for (String innerKeyword : withoutName) {
			try {
				keywords.add(Keyword.valueOf(innerKeyword.toUpperCase()));
			} catch (IllegalArgumentException e) {
				throw new IllegalStateException("Invalid keyword: " + innerKeyword, e);
			}
		}
		if (!equals.equals("=")) {
			return Optional.empty();
		}

		if (!keywords.contains(Keyword.VAL) && !keywords.contains(Keyword.VAR)) {
			return Optional.empty();
		}

		keywords.remove(Keyword.VAL);
		keywords.remove(Keyword.VAR);

		var name = s1.get(s1.size() - 1);
		if (tree.locateDeclaration(name).isPresent()) {
			throw new IllegalStateException(name + " is already declared.");
		}
		Node node = new DeclareNode(parser.parse(content), keywords.contains(Keyword.VAR),
				name, keywords);
		tree.append(node);
		return Optional.of(node);
	}

	private static class DeclareNode extends AbstractInheritedNode implements MutableNode, NamedNode {
		private final Collection<Keyword> keywords;
		private final boolean mutable;
		private final String name;

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

}
