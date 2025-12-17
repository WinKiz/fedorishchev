import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class PreciseCompoundInterest {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("ТОЧНЫЙ КАЛЬКУЛЯТОР СЛОЖНОГО ПРОЦЕНТА");
        System.out.println("(Используется BigDecimal для точности)\n");
        
        while (true) {
            System.out.println("Выберите операцию:");
            System.out.println("1. Расчет будущей стоимости");
            System.out.println("2. Расчет необходимой ставки");
            System.out.println("3. Выход");
            System.out.print("> ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    calculateFutureValuePrecise(scanner);
                    break;
                case "2":
                    calculateRequiredRatePrecise(scanner);
                    break;
                case "3":
                    System.out.println("Выход...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный выбор\n");
            }
        }
    }
    
    private static void calculateFutureValuePrecise(Scanner scanner) {
        try {
            System.out.print("Начальная сумма: ");
            BigDecimal principal = new BigDecimal(scanner.nextLine().replace(",", "."));
            
            System.out.print("Годовая ставка (%): ");
            BigDecimal rate = new BigDecimal(scanner.nextLine().replace(",", "."));
            
            System.out.print("Количество лет: ");
            int years = Integer.parseInt(scanner.nextLine());
            
            if (principal.compareTo(BigDecimal.ZERO) <= 0 || 
                rate.compareTo(BigDecimal.ZERO) <= 0 || years <= 0) {
                System.out.println("Все значения должны быть положительными!\n");
                return;
            }
            
            // Конвертируем процент в десятичную дробь
            BigDecimal rateDecimal = rate.divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);
            
            // Формула: A = P(1 + r)^n
            BigDecimal onePlusRate = BigDecimal.ONE.add(rateDecimal);
            BigDecimal futureValue = principal.multiply(onePlusRate.pow(years));
            
            BigDecimal totalInterest = futureValue.subtract(principal);
            
            System.out.println("\nРезультаты:");
            System.out.println("Будущая стоимость: " + 
                futureValue.setScale(2, RoundingMode.HALF_UP));
            System.out.println("Общий доход: " + 
                totalInterest.setScale(2, RoundingMode.HALF_UP));
            System.out.println();
            
        } catch (Exception e) {
            System.out.println("Ошибка ввода: " + e.getMessage() + "\n");
        }
    }
    
    private static void calculateRequiredRatePrecise(Scanner scanner) {
        try {
            System.out.print("Начальная сумма: ");
            BigDecimal start = new BigDecimal(scanner.nextLine().replace(",", "."));
            
            System.out.print("Целевая сумма: ");
            BigDecimal target = new BigDecimal(scanner.nextLine().replace(",", "."));
            
            System.out.print("Количество лет: ");
            int years = Integer.parseInt(scanner.nextLine());
            
            if (start.compareTo(BigDecimal.ZERO) <= 0 || 
                target.compareTo(BigDecimal.ZERO) <= 0 || years <= 0) {
                System.out.println("Все значения должны быть положительными!\n");
                return;
            }
            
            if (target.compareTo(start) <= 0) {
                System.out.println("Целевая сумма должна быть больше начальной!\n");
                return;
            }
            
            // Формула: r = (A/P)^(1/n) - 1
            BigDecimal ratio = target.divide(start, 10, RoundingMode.HALF_UP);
            
            // Используем приближенный расчет для корня n-й степени
            double ratioDouble = ratio.doubleValue();
            double root = Math.pow(ratioDouble, 1.0 / years);
            
            BigDecimal requiredRate = new BigDecimal(root - 1)
                .multiply(new BigDecimal("100"))
                .setScale(4, RoundingMode.HALF_UP);
            
            System.out.println("\nНеобходимая годовая ставка: " + requiredRate + "%");
            System.out.println();
            
        } catch (Exception e) {
            System.out.println("Ошибка ввода: " + e.getMessage() + "\n");
        }
    }
}
