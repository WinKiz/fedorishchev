import java.util.Scanner;

public class LeapYearChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.print("Введите год: ");
            int year = Integer.parseInt(scanner.nextLine());
            
            if (year <= 0) {
                throw new IllegalArgumentException("Год должен быть положительным числом");
            }
            
            boolean isLeap = checkLeapYear(year);
            
            if (isLeap) {
                System.out.println(year + " - високосный год");
            } else {
                System.out.println(year + " - не високосный год");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Введите корректный год (целое число)");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
    
    public static boolean checkLeapYear(int year) {
        // Год високосный, если:
        // 1. Делится на 400, ИЛИ
        // 2. Делится на 4, но НЕ делится на 100
        return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
    }
}
