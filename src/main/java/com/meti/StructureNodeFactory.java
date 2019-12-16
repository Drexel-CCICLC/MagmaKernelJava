package com.meti;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.meti.Bucket.build;

public class StructureNodeFactory implements NodeFactory {
	private final NodeTree tree;

	public StructureNodeFactory(NodeTree tree) {
		this.tree = tree;
	}

	@Override
	public Optional<Node> parse(String value, Parser parser, Node parent) {
		//(value string)=>int:{}
		BucketManager manager = new QueueBucketManager(
				build().include('(').restrict(1),
				build().exclude(')'),
				build().include(')').restrict(1),
				build().restrict(2),
				build().exclude(':'),
				build().include(':'),
				build()
		).pour(value.trim());
		var hasOpen = manager.next().equals("(");
		if (!hasOpen) return Optional.empty();
		var paramString = manager.next();
		var hasClosed = manager.next().equals(")");
		var hasReturn = manager.next().equals("=>");
		var returnString = manager.next();
		var hasImpl = manager.next().equals(":");
		var implString = manager.next();
		if (hasOpen || hasClosed) {
			var args = Arrays.stream(paramString.split(","))
					.filter(s -> !s.isBlank())
					.map(string -> string.trim().split(" "))
					.collect(Collectors.toMap(strings -> strings[0], strings -> parser.resolve(strings[1])));
			Struct returnType = null;
			if (hasReturn) {
				returnType = parser.resolve(returnString);
			}
			Struct struct = new FunctionStruct(args.values(), returnType);
			var node = new FunctionNode(struct, args);
			if (hasImpl) {
				var params = args.keySet()
						.stream()
						.map((Function<String, Node>) s -> new DeclareNode(args.get(s), s))
						.collect(Collectors.toList());
				tree.appendAll(params);
				node.setContent(parser.parse(implString, node));
				tree.removeAll(params);
			}
			return Optional.of(node);
		}
		return Optional.empty();
	}

	@Override
	public Optional<Struct> parse(String value) {
		return Optional.empty();
	}

	private static final class FunctionNode extends AbstractNode {
		private final Map<String, Struct> parameters;
		private Node content = null;

		protected FunctionNode(Struct struct, Map<String, Struct> parameters) {
			super(struct);
			this.parameters = parameters;
		}

		@Override
		public String compile(Aliaser aliaser) {
			if (content == null) {
				throw new IllegalStateException("Function is abstract.");
			}
			var contentString = content.compile(aliaser);
			if (!(content instanceof ParentNode)) {
				contentString = "{" + contentString + "}";
			}
			return "function(" + parameters.keySet()
					.stream()
					.map(aliaser::alias)
					.collect(Collectors.joining(",")) + ")" + contentString;
		}

		@Override
		public Node transform() {
			return this;
		}

		public void setContent(Node content) {
			this.content = content;
		}
	}

	private static final class FunctionStruct implements Struct {
		private final Collection<Struct> parameters;
		private final Struct returnType;

		private FunctionStruct(Collection<Struct> parameters, Struct returnType) {
			this.parameters = parameters;
			this.returnType = returnType;
		}

		@Override
		public boolean isInstance(Struct other) {
			return false;
		}

		@Override
		public Struct merge(Struct root) {
			if (isInstance(root)) {
				return this;
			}
			throw new IllegalArgumentException(root + " is not a struct.");
		}

		@Override
		public String name() {
			return "(" +
					parameters.stream()
							.map(Struct::name)
							.collect(Collectors.joining(",")) +
					")" +
					"=>" +
					(returnType == null ? "void" : returnType.name());
		}

		@Override
		public Optional<Struct> parent() {
			return Optional.empty();
		}
	}
}
