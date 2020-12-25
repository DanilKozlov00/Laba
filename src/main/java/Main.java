import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        String expression = "";
        Scanner scanner = new Scanner(System.in);
        while(!expression.equals("n")) {
            try {
                System.out.println("Введите выражение:");
                expression = scanner.nextLine();
                System.out.println("Создаем калькулятор...");
                Calculator calculator = new Calculator();
                ArrayList<Token> work;
                System.out.println("Токенизируем символы входной строки...");
                work = calculator.Tokenisation(expression);
                System.out.println("Предварительная обработка...");
                calculator.preprocessing(work);
                System.out.println("Преобразование выражения в постфиксную запись...");
                work = calculator.toPostfix(work);
                System.out.println("Вычисление...");
                System.out.println("Результат:" + calculator.calculate(work));
            } catch (EmptyStackException e) {
                System.out.println("Ошибка в выражении");
            } catch (NumberFormatException e) {
                System.out.println("Нечего вычислять");
            } catch (RuntimeException e) {
                System.out.println("Неизвестная ошибка");
            }
            System.out.println("Хотите продолжить? (y/n): ");
            expression = scanner.nextLine();
        }
    }
}

