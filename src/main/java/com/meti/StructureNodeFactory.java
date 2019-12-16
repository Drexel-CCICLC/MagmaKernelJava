package com.meti;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.meti.Bucket.build;

public class StructureNodeFactory implements NodeFactory {
	private final NodeTree tree;

	public StructureNodeFactory(NodeTree tree) {
		this.tree = tree;
	}

	@Override
	public Optional<Node> parse(String value, Parser parser) {
		//(value string)=>int:{}
		BucketManager manager = new QueueBucketManager(
				build().include('(').restrict(1),
				build().exclude(')'),
				build().include(')').restrict(1),
				build().restrict(2),
				build().exclude(':'),
				build().include(':'),
				build()
		).pour(value);
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
					.map(string -> string.split(" "))
					.collect(Collectors.toMap(strings -> strings[0], strings -> parser.resolve(strings[1])));
			Struct returnType = null;
			if (hasReturn) {
				returnType = parser.resolve(returnString);
			}
			var struct = new FunctionStruct(args.values(), returnType);
			Node impl = null;
			if (hasImpl) {
				var params = args.keySet()
						.stream()
						.map((Function<String, Node>) s -> new DeclareNode(args.get(s), s))
						.collect(Collectors.toList());
				tree.appendAll(params);
				impl = parser.parse(implString);
				tree.removeAll(params);
			}
			return Optional.of(new FunctionNode(struct, args, impl));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Struct> parse(String value) {
		return Optional.empty();
	}

	private static final class FunctionNode extends AbstractNode {
		private final Node content;
		private final Map<String, Struct> parameters;

		protected FunctionNode(Struct struct, Map<String, Struct> parameters, Node content) {
			super(struct);
			this.parameters = parameters;
			this.content = content;
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
