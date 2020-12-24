import java.util.EmptyStackException;
import java.util.Scanner;


public class Main {


    public static void main(String[] args) {
        try {
            Scanner xcan = new Scanner(System.in);
            System.out.println("Введите выражение:");
            String str = xcan.nextLine();
            Calculator calculator = new Calculator();
            System.out.println("Результат:" + calculator.calculatorWork(str));


        } catch (EmptyStackException e) {
            System.out.println("Ошибка в выражении");
        } catch (NumberFormatException e) {
            System.out.println("Нечего вычислять");
        } catch (RuntimeException e) {
            System.out.println("Неверный токен");
        }
    }


}

