package code;

import java.util.HashMap;

public class LL1Table {
    private HashMap<String, HashMap<String, String>> parseTable;

    public LL1Table() {
        parseTable = setupTable();
    }

    /*
    Non-terminals
    - M, E, U, X, P, L

    Types
    - epsi: epsilon (E)
    - '': actual epsilon
    - union: union
    - rep: repetition
    - term: terminal
     */
    private HashMap<String, HashMap<String, String>> setupTable() {
        HashMap<String, HashMap<String, String>> tempTable = new HashMap<>();

        HashMap<String, String> MRow = new HashMap<>();
        MRow.put("epsi", "E U");
        MRow.put("(", "E U");
        MRow.put("term", "E U");
        tempTable.put("M", MRow);

        HashMap<String, String> ERow = new HashMap<>();
        ERow.put("epsi", "epsi");
        ERow.put("(", "X");
        ERow.put("term", "X");
        tempTable.put("E", ERow);

        HashMap<String, String> URow = new HashMap<>();
        URow.put("union", "union E U");
        URow.put("(", "X U");
        URow.put(")", "");
        URow.put("term", "X U");
        URow.put("$", "");
        tempTable.put("U", URow);

        HashMap<String, String> XRow = new HashMap<>();
        XRow.put("(", "P L");
        XRow.put("term", "P L");
        tempTable.put("X", XRow);

        HashMap<String, String> PRow = new HashMap<>();
        PRow.put("(", "( M )");
        PRow.put("term", "term");
        tempTable.put("P", PRow);

        HashMap<String, String> LRow = new HashMap<>();
        LRow.put("union", "");
        LRow.put("(", "");
        LRow.put(")", "");
        LRow.put("term", "");
        LRow.put("rep", "rep");
        LRow.put("$", "");
        tempTable.put("L", LRow);
        return tempTable;
    }

    public HashMap<String, String> findDerivationFromKey(String key) {
        return parseTable.get(key);
    }

    public HashMap<String, HashMap<String, String>> getParseTable() {
        return parseTable;
    }
}
