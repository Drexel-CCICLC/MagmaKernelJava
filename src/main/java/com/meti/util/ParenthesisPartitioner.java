package com.meti.util;

import com.meti.exception.ParseException;

import java.util.List;

public class ParenthesisPartitioner implements Partitioner {
    private final String content;

    public ParenthesisPartitioner(String content) {
        this.content = content;
    }

    @Override
    public List<String> partition() {
        char[] charArray = content.toCharArray();
        int length = charArray.length;
        int depth = 0;
        int index = -1;
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
            throw new ParseException("Failed to break up: " + content);
        }
        String conditionString = content.substring(0, index);
        String blockString = content.substring(index + 1);
        return List.of(conditionString, blockString);
    }
}
