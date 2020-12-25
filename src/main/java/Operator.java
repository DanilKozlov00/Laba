import java.util.function.Function;

public class Operator {
    String content;
    int priority;
    Function<Double[], Double> function;


    public Operator(String content,  int priority, Function<Double[], Double> function) {
        this.content = content;
        this.priority = priority;
        this.function = function;
    }

    public static Double plus(Double ... args){
        return args[0]+args[1];
    }

    public static Double minus(Double ... args){
        return args[0]-args[1];
    }

    public static Double multiply(Double ... args){
        return args[0]*args[1];
    }

    public static Double divide(Double ... args) throws ArithmeticException{
        if(args[1]!=0)
            return args[0]/args[1];
        else throw new ArithmeticException("Попытка деления на ноль");
    }

    public static Double raiseDegree(Double ... args){
        return Math.pow(args[0], args[1]);
    }



}