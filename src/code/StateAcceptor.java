package code;

public class StateAcceptor {
    private int dfa;

    void start (char c) {
        if (c == 'E' || c == 'ε') dfa = 1;
        else if (c == 'U') dfa = 2;
        else if (c == 42 || c == 43 || c == 63 || c == 8727 || c == 726 || c == 5161 || c == 65110) dfa = 3;
        else if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z')) dfa = 4;
        else if (c == '(') dfa = 5;
        else if (c == ')') dfa = 6;
        else dfa = -1;
    }

    // epsilon
    void q1 (char c) {
        dfa = -1;
    }

    // union
    void q2 (char c) {
        dfa = -1;
    }

    // repetition
    void q3 (char c) {
        dfa = -1;
    }

    // terminal
    void q4 (char c) {
        if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z')) dfa = 4;
        else dfa = -1;
    }

    // left paren
    void q5 (char c) {
        dfa = -1;
    }

    // right paren
    void q6 (char c) {
        dfa = -1;
    }

    int getState(String s) {
        char c;
        dfa = 0;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            switch (dfa) {
                case 0: start(c); break;
                case 1: q1(c); break;
                case 2: q2(c); break;
                case 3: q3(c); break;
                case 4: q4(c); break;
                case 5: q5(c); break;
                case 6: q6(c); break;
                default: return 0;
            }
        }
        return dfa;
    }
}