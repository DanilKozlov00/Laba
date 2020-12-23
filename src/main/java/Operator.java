import java.util.function.Function;

public class Operator {
    String content;
    boolean isInString;
    int priority;
    int numberOfArguments;
    Function<Double[], Double> function;


    public Operator(String content, boolean isInString, int priority, int numberOfArguments, Function<Double[], Double> function) {
        this.content = content;
        this.isInString = isInString;
        this.priority = priority;
        this.numberOfArguments = numberOfArguments;
        this.function = function;
    }

    public static Double plus(Double[] args){
        return args[0]+args[1];
    }

    public static Double minus(Double[] args){
        return args[0]-args[1];
    }

    public static Double multiply(Double[] args){
        return args[0]*args[1];
    }

    public static Double divide(Double[] args) throws ArithmeticException{
        if(args[1]!=0)
            return args[0]/args[1];
        else throw new ArithmeticException("Попытка деления на ноль");
    }
}