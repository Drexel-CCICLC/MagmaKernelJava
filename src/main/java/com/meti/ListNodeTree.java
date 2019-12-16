package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListNodeTree implements NodeTree {
	private final List<Node> nodes;

	public ListNodeTree() {
		this(new ArrayList<>());
	}

	public ListNodeTree(List<Node> nodes) {
		this.nodes = nodes;
	}

	@Override
	public void append(Node node) {
		nodes.add(node);
	}

	@Override
	public void appendAll(List<Node> nodes) {
		this.nodes.addAll(nodes);
	}

	@Override
	public String compile(Aliaser aliaser) {
		return nodes.stream()
				.map(node -> node.compile(aliaser, this))
				.collect(Collectors.joining());
	}

	@Override
	public Optional<Node> locate(Predicate<Node> predicate) {
		return locate(nodes, predicate);
	}

	@Override
	public Optional<Node> locate(List<Node> nodes, Predicate<Node> filter) {
		for (Node node : nodes) {
			if (filter.test(node)) {
				return Optional.of(node);
			} else {
				if (node instanceof ParentNode) {
					ParentNode castedNode = (ParentNode) node;
					List<Node> children = castedNode.children();
					return locate(children, filter);
				}
			}
		}
		return Optional.empty();
	}

	@Override
	public MutableNode locateDeclaration(Node name) {
		MutableNode declaration;
		Optional<Node> optional =
				locate(node -> hasName(node, name));
		if (optional.isPresent()) {
			Node locatedNode = optional.get();
			if (locatedNode instanceof MutableNode) {
				declaration = (MutableNode) locatedNode;
			} else {
				throw new IllegalStateException(locatedNode + " is not a declaration.");
			}
		} else {
			throw new IllegalStateException(name + " is not defined.");
		}
		return declaration;
	}

	@Override
	public Optional<Node> locateDeclaration(String... name) {
		Optional<Node> current = Optional.empty();
		for (String s : name) {
			if (current.isEmpty()) {
				current = find(s, nodes);
			} else {
				var node = current.get();
				if (node instanceof InheritedNode) {
					var value = ((InheritedNode) node).value();
					if (value instanceof ParentNode && !(value instanceof InvocationNode)) {
						var children = ((ParentNode) value).children();
						current = find(s, children);
					} else {
						return parseObject(s, value);
					}
				} else {
					return Optional.empty();
				}
			}
		}
		return current;
	}

	@Override
	public void removeAll(List<Node> nodes) {
		this.nodes.removeAll(nodes);
	}

	@Override
	public void transform() {
		nodes.forEach(Node::transform);
	}

	private Optional<Node> find(String name, List<Node> list) {
		Node current;
		Node first = null;
		for (Node node : list) {
			if (node instanceof NamedNode && ((NamedNode) node).name().equals(name)) {
				first = node;
			}
		}
		if (first == null) {
			return Optional.empty();
		}
		current = first;
		return Optional.of(current);
	}

	private Optional<Node> parseObject(String s, Node value) {
		var structValue = value.struct();
		var isObject = structValue instanceof ObjectStruct;
		var parent = value.struct().parentNode().orElseThrow();
		if (isObject && (parent instanceof DeclareNode)) {
			var declaration = (DeclareNode) parent;
			FunctionNode function = (FunctionNode) declaration.value();
			BlockNode block = (BlockNode) function.getContent();
			return block.children().stream()
					.filter(node1 -> node1 instanceof NamedNode)
					.filter(node12 -> ((NamedNode) node12).name().equals(s))
					.findAny();
		} else {
			return Optional.empty();
		}
	}

	private boolean hasName(Node node, Node name) {
		return node instanceof InheritedNode && ((InheritedNode) node).value().equals(name);
	}
}
