import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    final public List<String> operators = Arrays.asList("+", "-", "/", "*");
    final public Pattern pattern = Pattern.compile("\\d+([.,]\\d+)?");

    public LinkedList<Token> Tokenisation(String expression) throws RuntimeException {
        LinkedList<Token> tokens = new LinkedList<>();
        int i = 0;
        while (i < expression.length()) {
            char tmp = expression.charAt(i);
            if (tmp == ' ' || tmp == '\t') {
                i++;
            } else if (tmp == '(') {
                tokens.add(new Token("(", Token.TokenType.OpenBrace, i));
                i++;
            } else if (tmp == ')') {
                tokens.add(new Token(")", Token.TokenType.CloseBrace, i));
                i++;
            } else {
                boolean foundOp = false;
                for (String op : operators)
                    if (expression.startsWith(op, i)) {
                        foundOp = true;
                        tokens.add(new Token(op, Token.TokenType.Operator, i));
                        i += op.length();
                        break;
                    }
                if (!foundOp) {
                    Matcher matcher = pattern.matcher(expression);
                    if (matcher.find(i) && matcher.start() == i) {
                        tokens.add(new Token(matcher.group(), Token.TokenType.Number, i));
                        i += matcher.group().length();
                    } else {
                        throw new RuntimeException("");
                    }
                }
            }
        }
        return tokens;
    }

    public void preprocessing(LinkedList<Token> tokens) {
        Token prev = null;
        boolean replace = false;
        for (int i = 0; i < tokens.size(); i++) {

            if (replace) {
                tokens.get(i).content = tokens.get(i - 1).content + tokens.get(i).content;
                tokens.remove(i - 1);
                i--;
                replace = false;
            }
            tokens.get(i).position = i;
            if (tokens.get(i).tType == Token.TokenType.Operator && tokens.get(i).content == "-") {
                if (prev == null || (prev.tType != Token.TokenType.Number && prev.tType != Token.TokenType.CloseBrace))
                    replace = true;
            } else if (tokens.get(i).tType == Token.TokenType.Operator && tokens.get(i).content == "+") {
                if (prev == null || (prev.tType != Token.TokenType.Number && prev.tType != Token.TokenType.CloseBrace))
                    replace = true;
            }
            prev = tokens.get(i);
        }

    }

    public LinkedList<Token> toPostfix(LinkedList<Token> tokens) {
        //TODO
        return null;
    }


    public Double calculate(LinkedList<Token> tokens) {
        //TODO
        return null;
    }

}
