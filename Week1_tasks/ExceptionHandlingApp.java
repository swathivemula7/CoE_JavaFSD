package week1;

import java.util.InputMismatchException;
import java.util.Scanner;

class ExceptionHandlingApp {
    public static void processInput() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter a number: ");
            double number = scanner.nextDouble();
            if (number == 0) {
                throw new ArithmeticException("Cannot calculate reciprocal of zero.");
            }
            double reciprocal = 1 / number;
            System.out.println("Reciprocal: " + reciprocal);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid number.");
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.close();
        }
    }

    public static void main(String[] args) {
        processInput();
    }
}