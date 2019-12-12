package com.meti;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DeclareNodeFactory implements NodeFactory {
	@Override
	public Optional<Node> parse(String value, Parser parser) {
		BucketManager manager = new QueueBucketManager(
				Bucket.build().exclude('='),
				Bucket.build().include('=').restrict(1),
				Bucket.build()).pour(value);
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
		keywords.remove(Keyword.VAL);
		keywords.remove(Keyword.VAR);
		return Optional.of(new DeclareNode(parser.parse(content), keywords.contains(Keyword.VAR),
				s1.get(s1.size() - 1), keywords));
	}

	private static class DeclareNode extends InheritedNode implements MutableNode {
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
					"var " + aliaser.alias(name) + "=" + node.compile(aliaser) + ";";
		}

		@Override
		public Node transform() {
			return this;
		}

		@Override
		public boolean isMutable() {
			return mutable;
		}
	}

}
