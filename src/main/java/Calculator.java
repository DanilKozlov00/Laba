import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    final public List<String> operators = Arrays.asList("+", "-", "/", "*");
    final public Pattern pattern = Pattern.compile("\\d+([.,]\\d+)?");

    private LinkedList<Token> Tokenisation(String expression) throws RuntimeException {
        LinkedList<Token> tokens = new LinkedList<>();
        int i = 0;
        while (i < expression.length()) {
            char tmp = expression.charAt(i);
            if (tmp == ' ' || tmp == '\t') {
                i++;
            } else if (tmp == '(') {
                tokens.add(new Token("(", Token.TokenType.OpenBrace));
                i++;
            } else if (tmp == ')') {
                tokens.add(new Token(")", Token.TokenType.CloseBrace));
                i++;
            } else {
                boolean foundOp = false;
                for (String op : operators)
                    if (expression.startsWith(op, i)) {
                        foundOp = true;
                        tokens.add(new Token(op, Token.TokenType.Operator));
                        i += op.length();
                        break;
                    }
                if (!foundOp) {
                    Matcher matcher = pattern.matcher(expression);
                    if (matcher.find(i) && matcher.start() == i) {
                        tokens.add(new Token(matcher.group(), Token.TokenType.Number));
                        i += matcher.group().length();
                    } else {
                        throw new RuntimeException("");
                    }
                }
            }
        }
        return tokens;
    }

    private void preprocessed(LinkedList<Token> tokens) {
        Token prev = null;
        boolean replace = false;
        for (int i = 0; i < tokens.size(); i++) {

            if (replace) {
                tokens.get(i).content = tokens.get(i - 1).content + tokens.get(i).content;
                tokens.remove(i - 1);
                i--;
                replace = false;
            }
            if (tokens.get(i).tType == Token.TokenType.Operator && tokens.get(i).content.equals("-")) {
                if (prev == null || (prev.tType != Token.TokenType.Number && prev.tType != Token.TokenType.CloseBrace))
                    replace = true;
            } else if (tokens.get(i).tType == Token.TokenType.Operator && tokens.get(i).content.equals("+")) {
                if (prev == null || (prev.tType != Token.TokenType.Number && prev.tType != Token.TokenType.CloseBrace))
                    replace = true;
            }
            prev = tokens.get(i);
        }

    }

    private LinkedList<Token> toPostfix(LinkedList<Token> tokens) {
        Stack<Token> op = new Stack<>();
        LinkedList<Token> result = new LinkedList<>();

        for (Token token : tokens) {
            if (token.tType == Token.TokenType.Number) {
                // Number, simply append to the result
                result.add(token);
            } else if (token.tType == Token.TokenType.OpenBrace) {
                // Left parenthesis, push to the stack
                op.push(token);
            } else if (token.tType == Token.TokenType.CloseBrace) {
                // Right parenthesis, pop and append to the result until meet the left parenthesis
                while (op.peek().tType != Token.TokenType.OpenBrace) {
                    result.add(op.pop());
                }
                // Don't forget to pop the left parenthesis out
                op.pop();
            } else if (token.tType == Token.TokenType.Operator) {
                // Operator, pop out all higher priority operators first and then push it to the stack
                while (!op.isEmpty() && Operator.priority(op.peek()) >= Operator.priority(token)) {
                    result.add(op.pop());
                }
                op.push(token);
            }
        }

        while (!op.isEmpty()) {
            result.add(op.pop());
        }
        return result;
    }


    private Double calculate(LinkedList<Token> tokens) {
        Stack<Double> result = new Stack<>();
        Double num;
        if (tokens.size() == 1) return Double.parseDouble(tokens.get(0).content);
        if (tokens.size() == 2 || tokens.get(1).tType == Token.TokenType.Operator) {
            return Double.parseDouble(tokens.get(0).content);
        }

        for (int i = 0; i < tokens.size(); i++) {
            result.push(Double.parseDouble(tokens.get(i).content));
            if (result.size() == 2) {
                i++;
                double second = result.pop();
                switch (tokens.get(i).content) {
                    case "*":
                        result.push(Operator.multiply(result.pop(), second));
                        break;
                    case "/":
                        result.push(Operator.divide(result.pop(), second));
                        break;
                    case "+":
                        result.push(Operator.plus(result.pop(), second));
                        break;
                    case "-":
                        result.push(Operator.minus(result.pop(), second));
                        break;
                }
            }
        }
        return result.pop();
    }

    public Double calculatorWork(String expression) {
        LinkedList<Token> work = new LinkedList<>();
        work = Tokenisation(expression);
        preprocessed(work);
        work = toPostfix(work);
        return calculate(work);
    }
}
