package code;
import java.util.ArrayList;

public class LexicalAnalyzer {

    private String[] sourceLines;
    private int[] lineIndexes;
    private ArrayList<Token> tokenList;

    public LexicalAnalyzer() {
    }

    private boolean isAlphaNumeric(char c) {
        return (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9');
    }

    private boolean isWhitespace(char c) {
        return c == 10 || c == 32;
    }

    private ArrayList<Token> findTokens(String s) {
        ArrayList<Token> lineTokens = new ArrayList<>();
        StringBuilder tokenSb = new StringBuilder();
        Token t;
        boolean alphaPrev = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (isWhitespace(c)) {
                if (alphaPrev) {
                    t = new Token(tokenSb.toString());
                    lineTokens.add(t);
                    tokenSb.setLength(0);
                }
                while (isWhitespace(c) && i <= s.length()) {
                    i++;
                    c = s.charAt(i);
                }
                i--;
                alphaPrev = false;
            } else if (isAlphaNumeric(c)) {
                tokenSb.append(c);
                alphaPrev = true;
            } else {
                if (alphaPrev) {
                    t = new Token(tokenSb.toString());
                    lineTokens.add(t);
                    tokenSb.setLength(0);
                }
                t = new Token(Character.toString(c));
                lineTokens.add(t);
                alphaPrev = false;
            }
        }
        if (alphaPrev) {
            t = new Token(tokenSb.toString());
            lineTokens.add(t);
            tokenSb.setLength(0);
        }
        return lineTokens;
    }

    ArrayList<Token> process(String sourceCode) {
        tokenList = new ArrayList<>();
        sourceLines = sourceCode.split("\\n");
        int count = 0;
        for (String s : sourceLines) {
            if (s.trim().length() > 0) {
                count++;
            }
        }
        lineIndexes = new int[count];
        int j = 0;
        for (String s : sourceLines) {
            if (s.trim().length() > 0) {
                ArrayList<Token> tempAL = findTokens(s.trim());
                int tempCount = tempAL.size();
                if (j == 0) {
                    lineIndexes[0] = tempCount - 1;
                } else {
                    lineIndexes[j] = lineIndexes[j - 1] + tempCount;
                }
                j++;
                tokenList.addAll(tempAL);
            }
        }
        return tokenList;
    }

    public ArrayList<Token> getTokenList() {
        return tokenList;
    }

    public String[] getSourceLines() {
        return sourceLines;
    }

    public int[] getLineIndexes() {
        return lineIndexes;
    }
}