import java.util.Scanner;



public class Main {


    public static void main(String[] args){
        try{
            Calculator calculator = new Calculator();
            var tokens = calculator.Tokenisation("(2*-3)");
            calculator.preprocessing(tokens);
            System.out.println(tokens);
        }
        catch (ArithmeticException e) {
            System.out.println(e);
        }
        catch (RuntimeException e){
            System.out.println(e);
        }
    }

    public void Scaner(){
        Scanner xcan = new Scanner(System.in);
        String str = xcan.nextLine();
    }

}

