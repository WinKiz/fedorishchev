import java.util.Scanner;

public class Calculator {
    
    // Метод для вычисления выражения
    public static double calculate(String expression) throws IllegalArgumentException {
        // Убираем пробелы в начале и конце
        expression = expression.trim();
        
        if (expression.isEmpty()) {
            throw new IllegalArgumentException("Пустое выражение!");
        }
        
        // Ищем оператор
        char operator = findOperator(expression);
        
        // Разделяем строку по оператору
        String[] parts = expression.split("\\s*[" + operator + "]\\s*", 2);
        
        if (parts.length != 2) {
            throw new IllegalArgumentException("Некорректное выражение: " + expression);
        }
        
        // Парсим числа
        double num1, num2;
        try {
            num1 = Double.parseDouble(parts[0].trim());
            num2 = Double.parseDouble(parts[1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректные числа в выражении: " + expression);
        }
        
        // Проверяем деление на ноль
        if (operator == '/' && num2 == 0) {
            throw new ArithmeticException("Деление на ноль!");
        }
        
        // Выполняем операцию
        return performOperation(num1, num2, operator);
    }
    
    // Метод для поиска оператора в выражении
    private static char findOperator(String expression) {
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (isOperator(c) && !isPartOfNumber(expression, i)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Оператор не найден в выражении: " + expression);
    }
    
    // Проверка, является ли символ оператором
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
    
    // Проверка, является ли символ частью числа (например, минус в отрицательном числе)
    private static boolean isPartOfNumber(String expression, int index) {
        char c = expression.charAt(index);
        
        // Если это минус и он в начале строки или после другого оператора
        if (c == '-') {
            // Проверяем контекст
            if (index == 0) return true;
            
            // Ищем предыдущий не-пробельный символ
            for (int i = index - 1; i >= 0; i--) {
                char prev = expression.charAt(i);
                if (!Character.isWhitespace(prev)) {
                    return isOperator(prev);
                }
            }
        }
        return false;
    }
    
    // Метод для выполнения операции
    private static double performOperation(double num1, double num2, char operator) {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Неизвестный оператор: " + operator);
        }
    }
    
    // Метод для форматирования вывода
    private static String formatResult(double result) {
        // Если результат целое число, выводим без десятичной части
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            // Ограничиваем количество знаков после запятой
            return String.format("%.4f", result).replaceAll("0*$", "").replaceAll("\\.$", "");
        }
    }
    
    // Главный метод
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("====================================");
        System.out.println("    КАЛЬКУЛЯТОР v1.0");
        System.out.println("====================================");
        System.out.println("Поддерживаемые операции: + - * /");
        System.out.println("Примеры ввода: 2 + 3, 4.5 * 2, 10 / 3");
        System.out.println("Введите 'exit' для выхода");
        System.out.println("====================================\n");
        
        while (true) {
            System.out.print("Введите выражение: ");
            String input = scanner.nextLine().trim();
            
            // Проверка на выход
            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                System.out.println("До свидания!");
                break;
            }
            
            if (input.equalsIgnoreCase("help")) {
                showHelp();
                continue;
            }
            
            try {
                double result = calculate(input);
                String formattedResult = formatResult(result);
                
                System.out.printf("Результат: %s = %s%n%n", input, formattedResult);
                
            } catch (IllegalArgumentException e) {
                System.err.println("Ошибка: " + e.getMessage());
                System.out.println("Попробуйте еще раз или введите 'help' для справки\n");
            } catch (ArithmeticException e) {
                System.err.println("Математическая ошибка: " + e.getMessage() + "\n");
            } catch (Exception e) {
                System.err.println("Неожиданная ошибка: " + e.getMessage() + "\n");
            }
        }
        
        scanner.close();
    }
    
    
    }
}
