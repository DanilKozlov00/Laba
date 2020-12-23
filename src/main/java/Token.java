public class Token {
    String content;
    TokenType tType;
    int position;

    public enum TokenType{
        Operator,
        Number,
        OpenBrace,
        CloseBrace
    }

    public Token(String content,TokenType type, int position){
        this.content=content;
        this.tType=type;
        this.position=position;
    }

    @Override
    public String toString() {
        return String.format("%s '%s' at %d", tType.toString(), content, position);
    }
}
