import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    final public List<Operator> operators = new ArrayList<>();
    final public List<String> operatorStrings = Arrays.asList("+", "-", "/", "*", "^");
    final public Pattern pattern = Pattern.compile("-?\\d+([.,]\\d+)?");

    public Calculator() {
        operators.add(new Operator("none",  0,  null));
        operators.add(new Operator("+",  1,  Operator::plus));
        operators.add(new Operator("-",  1,  Operator::minus));
        operators.add(new Operator("*",  2,  Operator::multiply));
        operators.add(new Operator("/",  2,  Operator::divide));
        operators.add(new Operator("^",  3,  Operator::raiseDegree));
    }

    public ArrayList<Token> Tokenisation(String expression) throws RuntimeException {
        ArrayList<Token> tokens = new ArrayList<>();
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
                for (String op : operatorStrings)
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
                        throw new RuntimeException("Неизвестный символ на позиции: " + i);
                    }
                }
            }
        }
        return tokens;
    }

    public void preprocessing(ArrayList<Token> tokens) {
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

    public ArrayList<Token> toPostfix(ArrayList<Token> tokens) {
        Stack<Token> op = new Stack<>();
        ArrayList<Token> result = new ArrayList<>();

        for (Token token : tokens) {
            if (token.tType == Token.TokenType.Number) {
                result.add(token);
            } else if (token.tType == Token.TokenType.OpenBrace) {
                op.push(token);
            } else if (token.tType == Token.TokenType.CloseBrace) {
                while (op.peek().tType != Token.TokenType.OpenBrace) {
                    result.add(op.pop());
                }
                op.pop();
            } else if (token.tType == Token.TokenType.Operator) {
                while (!op.isEmpty() && getOperatorFromToken(op.peek()).priority >= getOperatorFromToken(token).priority) {
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

    public Operator getOperatorFromToken(Token tmp) {
        if (tmp.tType == Token.TokenType.Operator) {
            Operator tmpOp;
            for (Operator o : operators) {
                if (o.content.equals(tmp.content)) {
                    tmpOp = o;
                    return tmpOp;
                }
            }
            throw new RuntimeException("Токен является оператором, которого нет в базе");
        } else {
            return operators.get(0);
        }
    }

    public Double calculate(ArrayList<Token> tokens) {
        Stack<Double> Arguments = new Stack<>();
        Double[] args = new Double[2];
        for (Token t : tokens)
            if (t.tType == Token.TokenType.Number) {
                Double v = Double.parseDouble(t.content);
                Arguments.push(v);
            } else {
                int nArg = /*колво аргументов оператора*/ 2;
                for (int i = nArg - 1; i >= 0; i--) {
                    args[i] = Arguments.pop();
                }
                Operator tmpOp = getOperatorFromToken(t);
                Function<Double[], Double> fn = tmpOp.function;
                Double res = fn.apply(args);
                Arguments.push(res);
            }
        if (Arguments.size() == 1)
            return Arguments.pop();
        else
            throw new RuntimeException("Осталось что-то лишнее");
    }
}
