public class Token {
    public String content;
    TokenType tType;

    public enum TokenType {
        Operator,
        Number,
        OpenBrace,
        CloseBrace
    }

    public Token(String content, TokenType type) {
        this.content = content;
        this.tType = type;
    }

    @Override
    public String toString() {
        return String.format("%s '%s'", tType.toString(), content);
    }
}
