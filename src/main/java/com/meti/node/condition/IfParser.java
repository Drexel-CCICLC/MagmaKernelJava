package com.meti.node.condition;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.exception.ParseException;
import com.meti.node.Node;

import java.util.Optional;

public class IfParser implements Parser {
    @Override
    public Optional<Node> parse(String content, Compiler compiler) {
        String trim = content.trim();
        if (trim.startsWith("if(")) {
            int index = -1;
            int depth = 0;
            String withoutHeader = trim.substring(3);
            char[] charArray = withoutHeader.toCharArray();
            int length = charArray.length;
            for (int i = 0; i < length; i++) {
                char c = charArray[i];
                if (c == ')' && depth == 0) {
                    index = i;
                    break;
                } else {
                    if (c == '(') depth++;
                    if (c == ')') depth--;
                }
            }
            if (index == -1) {
                throw new ParseException("Could not resolve condition of:" + trim);
            }
            String conditionString = withoutHeader.substring(0, index);
            String blockString = withoutHeader.substring(index + 1);
            Node condition = compiler.parse(conditionString);
            Node block = compiler.parse(blockString);
            return Optional.of(new IfNode(condition, block));
        }
        return Optional.empty();
    }
}
