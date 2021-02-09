package code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.TreeMap;

public class LL1Parser {

    private LL1Table table;

    private ArrayList<Token> tokenList;
    private ArrayList<String> inputLines;
    private String[] codeLines;
    private int[] lineIndexes;
    private ArrayList<String> outputLines;

    public LL1Parser(ArrayList<Token> tokenList, String[] codeLines, int[] lineIndexes) {
        // pass values
        this.tokenList = tokenList;
        this.codeLines = codeLines;
        this.lineIndexes = lineIndexes;

        // initialize data structures
        table = new LL1Table();
        inputLines = new ArrayList<>();
        outputLines = new ArrayList<>();

        // set up lines
        int j = 0, i = 0, count = 0;
        StringBuilder sb = new StringBuilder();
        for (String s : codeLines) {
            if (s.trim().length() > 0) {
                for (; i <= lineIndexes[j]; i++) {
                    String b = tokenToGrammar(tokenList.get(i).getTokenType().toString());
                    sb.append(b);
                    sb.append(" ");
                }
                inputLines.add(sb.toString());
                sb.setLength(0);
                lineIndexes[j] = count;
                j++;
            }
            count++;
        }
    }

    /*
    Non-terminals
    - M, E, U, X, P, L

    Types
    - epsi: epsilon
    - union: union
    - rep: repetition
    - term: terminal
     */
    private String tokenToGrammar (String type) {
        if (type.equals("EPSILON"))
            return "epsi";
        if (type.equals("UNION"))
            return "union";
        if (type.equals("REPETITION"))
            return "rep";
        if (type.equals("TERMINAL"))
            return "term";
        if (type.equals("LEFT_P"))
            return "(";
        if (type.equals("RIGHT_P"))
            return ")";
        return "ERROR";
    }

    public ArrayList<String> getOutputLines() {
        return outputLines;
    }

    public ArrayList<Token> getTokenList() {
        return tokenList;
    }

    /*
    Grammar:
        M : E U ;
        E : X | epsilon;
        U : union E U | X U ;
        X : P L ;
        P : leftp M rightp | terminal ;
        L : repetition ;
    */

    void parse() {
        Stack<String> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inputLines.size(); i++) {
            stack.push("$"); // start symbol
            stack.push("M"); // first non-terminal
            if (inputLines.get(i).contains("ERROR")) {
                outputLines.add(codeLines[lineIndexes[i]] + " - REJECT");
            } else {
                sb.setLength(0);
                sb.append(codeLines[lineIndexes[i]]);
                if (isValid(inputLines.get(i), stack)) {
                    sb.append(" - ACCEPT");
                } else {
                    sb.append(" - REJECT");
                }
                outputLines.add(sb.toString());
            }
        }
    }

    public void printOutputLines() {
        int i = 1;
        for (String s : outputLines) {
//            System.out.print(i + ": ");
            System.out.println(s);
            i++;
        }
    }

    public boolean isValid(String string, Stack<String> stack) {
        int pointer = 0;
        string = string.concat("$");
        String[] inputs = string.split(" ");
        try {
            while (!stack.empty() && pointer < inputs.length) {
                String top = stack.peek();
                String stringP = inputs[pointer];

                if (top.equals("$")) {
                    if (stringP.equals("$")) {
                        stack.pop();
                        pointer++;
                    } else {
                        throw new Exception();
                    }
                } else if (top.equals("")) {
                    stack.pop();
                } else if (isTerminal(top)) {
                    if (top.equals(stringP)) {
                        pointer++;
                        stack.pop();
                    } else return false;
                } else if (isNonTerminal(top)) {
                    HashMap<String, String> derivation = table.findDerivationFromKey(top);
                    if (derivation.isEmpty()) {
                        throw new Exception();
                    } else {
                        String d = derivation.get(stringP);
                        String[] symbols = d.split(" ");
                        stack.pop();
                        int symbolIndex = symbols.length - 1;
                        while (symbolIndex > -1) {
                            stack.push(symbols[symbolIndex]);
                            symbolIndex--;
                        }
                    }
                } else {
                    pointer++;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean isTerminal (String string) {
        return string.equals("epsi")
                || string.equals("term")
                || string.equals("union")
                || string.equals("rep")
                || string.equals("(")
                || string.equals(")")
                || string.equals("ee")
                ;
    }

    public boolean isNonTerminal (String string) {
        //M, E, U, X, P, L
        return
                string.equals("M")
                || string.equals("E")
                || string.equals("U")
                || string.equals("X")
                || string.equals("P")
                || string.equals("L")
                ;
    }
}
