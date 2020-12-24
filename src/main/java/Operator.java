import java.util.function.Function;

public class Operator {

    public static Double plus(double first, double second) {
        return first + second;
    }

    public static Double minus(double first, double second) {
        return first - second;
    }

    public static Double multiply(double first, double second) {
        return first * second;
    }

    public static Double divide(double first, double second) throws ArithmeticException {
        if (first != 0)
            return first / second;
        else throw new ArithmeticException("Попытка деления на ноль");
    }

    public static int priority(Token token) {
        switch (token.content) {
            case "^":
                return 3;
            case "*":
            case "/":
                return 2;
            case "+":
            case "-":
                return 1;
        }
        return 0;
    }
}