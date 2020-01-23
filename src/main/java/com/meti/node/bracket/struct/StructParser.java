package com.meti.node.bracket.struct;

import com.meti.compile.Compiler;
import com.meti.declare.Declaration;
import com.meti.declare.Declarations;
import com.meti.declare.Parameter;
import com.meti.node.EmptyNode;
import com.meti.node.Node;
import com.meti.node.Parser;
import com.meti.node.Type;
import com.meti.node.bracket.block.BlockNode;
import com.meti.node.value.primitive.array.Cache;

import java.util.*;
import java.util.stream.Collectors;

public class StructParser implements Parser {
    private final Declarations declarations;
    private final Cache cache;
    private final Generator generator;

    public StructParser(Declarations declarations, Cache cache) {
        this(declarations, cache, new IncrementedGenerator());
    }

    public StructParser(Declarations declarations, Cache cache, Generator generator) {
        this.declarations = declarations;
        this.generator = generator;
        this.cache = cache;
    }

    @Override
    public Optional<Node> parse(String value, Compiler compiler) {
        String content = value.trim();
        if (content.startsWith("(")) {
            Node node = build(compiler, content);
            return Optional.of(node);
        }
        return Optional.empty();
    }

    private Node build(Compiler compiler, String content) {
        List<Parameter> parameters = parseAllParameters(compiler, content);
        Type returnType = resolveReturnType(compiler, content);
        Node block = buildConcreteBlock(compiler, content, parameters);
        cache.add(declarations.current().asStructBuilder(parameters, returnType, block).create(generator));
        return new EmptyNode();
    }

    private List<Parameter> parseAllParameters(Compiler compiler, String content) {
        List<Parameter> parameters = new ArrayList<>();
        parameters.addAll(parseParameters(compiler, content));
        parameters.addAll(buildParentParameters());
        parameters.forEach(declarations::define);
        return parameters;
    }

    private Type resolveReturnType(Compiler compiler, String content) {
        int returnTypeStart = content.indexOf("=>") + 2;
        int returnTypeEnd = hasImplementation(content) ? content.indexOf(':') : content.length();
        String returnTypeString = content.substring(returnTypeStart, returnTypeEnd);
        return compiler.resolveName(returnTypeString);
    }

    private Node buildConcreteBlock(Compiler compiler, String content, List<? extends Parameter> parameters) {
        if (hasImplementation(content)) {
            return buildBlock(compiler, content, parameters);
        } else {
            throw new UnsupportedOperationException("Abstract methods are not supported yet.");
        }
    }

    private Collection<? extends Parameter> parseParameters(Compiler compiler, String content) {
        int paramStart = content.indexOf('(') + 1;
        int paramEnd = content.indexOf(')');
        String paramsString = content.substring(paramStart, paramEnd);
        return Arrays.stream(paramsString.split(","))
                .map(String::trim)
                .filter(paramString -> !paramString.isBlank())
                .map(value -> Parameter.of(value, compiler))
                .collect(Collectors.toSet());
    }

    private Set<Parameter> buildParentParameters() {
        return declarations.stream()
                .filter(declaration -> !declarations.isRoot(declaration) &&
                        !declarations.isCurrent(declaration))
                .map(Declaration::toInstancePair)
                .collect(Collectors.toSet());
    }

    private boolean hasImplementation(String content) {
        return content.contains(":");
    }

    private Node buildBlock(Compiler compiler, String content, List<? extends Parameter> parameters) {
        Node block = parseBlock(compiler, content);
        buildInstance(parameters, block);
        return block;
    }

    private Node parseBlock(Compiler compiler, String content) {
        String blockString = content.substring(content.indexOf(':') + 1);
        Node impl = compiler.parseSingle(blockString);
        return impl.isParent() ? impl : new BlockNode(Collections.singleton(impl));
    }

    private void buildInstance(List<? extends Parameter> parameters, Node block) {
        Declaration current = declarations.current();
        Optional<Declaration> parent = current.parent();
        if (parent.isPresent() && !declarations.isRoot(parent.get())) {
            Node struct = current.toStruct(parameters);
            cache.add(struct);
            Deque<Node> children = block.children();
            children.addFirst(current.toStructDeclaration(parameters));
        }
    }
}
