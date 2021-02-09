package code;

import java.util.ArrayList;

public class Rule {
    private String LHS;
    private ArrayList<String[]> RHS;

    public Rule() {
        RHS = new ArrayList<>();
    }

    public void setLHS(String LHS) {
        this.LHS = LHS;
    }

    public String getLHS() {
        return LHS;
    }

    public ArrayList<String[]> getRHS() {
        return RHS;
    }
}