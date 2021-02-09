package code;

public class Token {
    private String lexeme;
    private static StateAcceptor stateAcceptor = new StateAcceptor();

    public enum TokenType {
        TERMINAL,
        REPETITION,
        EPSILON,
        UNION,
        LEFT_P,
        RIGHT_P,
        ERROR
    }

    private TokenType tokenType;

    public Token(String word) {
        stateAcceptor = new StateAcceptor();
        this.tokenType = identifyToken(word);
        this.lexeme = word;
    }

    private static TokenType identifyToken(String word) {
        int state = stateAcceptor.getState(word);
        switch (state) {
            case 1: return TokenType.EPSILON;
            case 2: return TokenType.UNION;
            case 3: return TokenType.REPETITION;
            case 4: return TokenType.TERMINAL;
            case 5: return TokenType.LEFT_P;
            case 6: return TokenType.RIGHT_P;
            default: return TokenType.ERROR;
        }
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getLexeme() {
        return lexeme;
    }
}