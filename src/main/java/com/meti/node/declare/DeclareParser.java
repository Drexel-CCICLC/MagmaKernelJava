package com.meti.node.declare;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.exception.StateException;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.parse.Declarations;
import com.meti.parse.Flag;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DeclareParser implements Parser {
    public static final String EMPTY_STRING = "";
    private final Declarations declarations;

    public DeclareParser(Declarations declarations) {
        this.declarations = declarations;
    }

    @Override
    public Optional<Node> parse(String content, Compiler compiler) {
        return Optional.of(content)
                .map(String::trim)
                .filter(s -> s.contains("="))
                .filter(s -> isValid(s, s.indexOf('=')))
                .map(s -> parseValid(compiler, s));
    }

    Node parseValid(Compiler compiler, String trim) {
        String head = parseHead(trim);
        Set<Flag> flags = parseFlagSet(head);
        String name = parseName(head);
        String value = parseValue(trim);
        return isDeclaration(flags) ?
                buildDeclaration(compiler, flags, name, value) :
                buildAssignment(compiler, head, value);
    }

    private String parseHead(String trim) {
        int equalsIndex = trim.indexOf('=');
        String head = trim.substring(0, equalsIndex);
        return head.trim();
    }

    private String parseValue(String trim) {
        int equalsIndex = trim.indexOf('=');
        String value = trim.substring(equalsIndex + 1);
        return value.trim();
    }

    private String parseName(String head) {
        return parseNameString(head, head.lastIndexOf(' '));
    }

    private Set<Flag> parseFlagSet(String head) {
        String flagString = parseFlagString(head, head.lastIndexOf(' '));
        return parseFlags(flagString);
    }

    private String parseNameString(String beforeEquals, int lastSpace) {
        return lastSpace == -1 ?
                beforeEquals :
                beforeEquals.substring(lastSpace + 1);
    }

    private String parseFlagString(String beforeEquals, int lastSpace) {
        return -1 == lastSpace ?
                EMPTY_STRING :
                beforeEquals.substring(0, lastSpace);
    }

    private Node buildDeclaration(Compiler compiler, Set<Flag> flags, String name, String value) {
        String formattadName = formatName(name, flags);
        if (declarations.isDefined(formattadName)) throw new StateException(formattadName + " is already defined.");
        return declarations.inStack(formattadName, s -> renderInStack(compiler, flags, s, value));
    }

    private boolean isDeclaration(Collection<Flag> flags) {
        return flags.contains(Flag.VAR) || flags.contains(Flag.VAL);
    }

    private String formatName(String nameString, Collection<Flag> flags) {
        return flags.contains(Flag.SINGLE) ? nameString + "$" : nameString;
    }

    private Node buildAssignment(Compiler compiler, String to, String from) {
        Node fromNode = compiler.parse(from);
        Node toNode = compiler.parse(to);
        return new AssignNode(toNode, fromNode);
    }

    private Node renderInStack(Compiler compiler, Set<Flag> flags, String name, String valueString) {
        Set<Flag> previousFlags = declarations.swapFlags(flags);
        Node node = buildNode(compiler, flags, name, valueString);
        declarations.swapFlags(previousFlags);
        return node;
    }

    private Node buildNode(Compiler compiler, Set<Flag> flags, String name, String valueString) {
        Type type = compiler.resolveValue(valueString);
        declarations.defineParent(type, name, flags);
        Node value = compiler.parse(valueString);
        return new DeclareNode(type, name, value);
    }

    private Set<Flag> parseFlags(String flagString) {
        return Arrays.stream(flagString.split(" "))
                .filter(s -> !s.isBlank())
                .map(String::toUpperCase)
                .map(Flag::valueOf)
                .collect(Collectors.toSet());
    }

    private boolean isValid(String trim, int equalsIndex) {
        String twoString = trim.substring(equalsIndex, equalsIndex + 2);
        return "==".equals(twoString) ?
                "==>".equals(trim.substring(equalsIndex, equalsIndex + 3)) :
                !"!=".equals(trim.substring(equalsIndex - 1, equalsIndex + 1));
    }
}
