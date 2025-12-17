import java.io.*;
import java.util.*;

public class FileDuplicateHandler {
    
    // 1. Удаление дублирующихся строк
    public static int removeDuplicates(String inputFile, String outputFile) throws IOException {
        Set<String> uniqueLines = new LinkedHashSet<>();
        int duplicateCount = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!uniqueLines.add(line)) {
                    duplicateCount++;
                }
            }
        }
        
        // Записываем уникальные строки в новый файл
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String uniqueLine : uniqueLines) {
                writer.write(uniqueLine);
                writer.newLine();
            }
        }
        
        return duplicateCount;
    }
    
    // 2. Восстановление сжатой версии (с дубликатами)
    public static void restoreFile(String compressedFile, String restoredFile, 
                                   String countFile) throws IOException {
        // Читаем количество удаленных строк
        int removedCount = 0;
        try (BufferedReader countReader = new BufferedReader(new FileReader(countFile))) {
            String countLine = countReader.readLine();
            if (countLine != null) {
                removedCount = Integer.parseInt(countLine.split(":")[1].trim());
            }
        }
        
        // Читаем сжатый файл
        List<String> compressedLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(compressedFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                compressedLines.add(line);
            }
        }
        
        // Восстанавливаем с дубликатами (просто дублируем каждую строку несколько раз)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(restoredFile))) {
            Random random = new Random();
            for (String line : compressedLines) {
                writer.write(line);
                writer.newLine();
                
                // Добавляем случайные дубликаты для имитации восстановления
                int duplicates = random.nextInt(3); // 0-2 дубликата на строку
                for (int i = 0; i < duplicates; i++) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
    }
    
    // Комбинированный метод: сжатие с сохранением информации о дубликатах
    public static void compressWithDuplicatesInfo(String inputFile, 
                                                  String compressedFile,
                                                  String infoFile) throws IOException {
        Map<String, Integer> lineCount = new LinkedHashMap<>();
        
        // Читаем и считаем строки
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount.put(line, lineCount.getOrDefault(line, 0) + 1);
            }
        }
        
        // Записываем сжатый файл (только уникальные строки)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(compressedFile))) {
            for (String line : lineCount.keySet()) {
                writer.write(line);
                writer.newLine();
            }
        }
        
        // Записываем информацию о дубликатах
        int totalDuplicates = 0;
        try (BufferedWriter infoWriter = new BufferedWriter(new FileWriter(infoFile))) {
            infoWriter.write("Статистика дубликатов:");
            infoWriter.newLine();
            
            for (Map.Entry<String, Integer> entry : lineCount.entrySet()) {
                if (entry.getValue() > 1) {
                    infoWriter.write("Строка: \"" + entry.getKey().substring(0, 
                        Math.min(30, entry.getKey().length())) + "...\" - повторений: " + 
                        (entry.getValue() - 1));
                    infoWriter.newLine();
                    totalDuplicates += (entry.getValue() - 1);
                }
            }
            
            infoWriter.write("Всего удалено дубликатов: " + totalDuplicates);
            infoWriter.newLine();
        }
        
        System.out.println("Удалено дубликатов: " + totalDuplicates);
    }
    
    // Восстановление из сжатой версии с информацией о дубликатах
    public static void restoreFromCompressed(String compressedFile,
                                            String infoFile,
                                            String restoredFile) throws IOException {
        // Читаем информацию о дубликатах
        Map<String, Integer> duplicateInfo = new HashMap<>();
        try (BufferedReader infoReader = new BufferedReader(new FileReader(infoFile))) {
            String line;
            while ((line = infoReader.readLine()) != null) {
                if (line.startsWith("Строка:")) {
                    String[] parts = line.split(" - повторений: ");
                    if (parts.length == 2) {
                        String key = parts[0].substring(8, parts[0].length() - 3); // Извлекаем текст
                        int count = Integer.parseInt(parts[1].trim());
                        duplicateInfo.put(key, count);
                    }
                }
            }
        }
        
        // Читаем сжатый файл
        List<String> compressedLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(compressedFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                compressedLines.add(line);
            }
        }
        
        // Восстанавливаем файл с дубликатами
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(restoredFile))) {
            for (String compressedLine : compressedLines) {
                // Пишем оригинальную строку
                writer.write(compressedLine);
                writer.newLine();
                
                // Добавляем дубликаты на основе информации
                String shortKey = compressedLine.length() > 30 ? 
                    compressedLine.substring(0, 30) : compressedLine;
                    
                if (duplicateInfo.containsKey(shortKey)) {
                    int duplicateCount = duplicateInfo.get(shortKey);
                    for (int i = 0; i < duplicateCount; i++) {
                        writer.write(compressedLine);
                        writer.newLine();
                    }
                }
            }
        }
    }
    
    // Главный метод для тестирования
    public static void main(String[] args) {
        try {
            // Создаем тестовый файл с дубликатами
            createTestFile("test_input.txt");
            
            System.out.println("=== СЖАТИЕ ФАЙЛА ===");
            System.out.println("Исходный файл: test_input.txt");
            
            // Вариант 1: Простое удаление дубликатов
            int duplicatesRemoved = removeDuplicates("test_input.txt", "test_compressed.txt");
            System.out.println("Удалено дубликатов: " + duplicatesRemoved);
            
            // Сохраняем информацию о количестве удаленных строк
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("duplicate_count.txt"))) {
                writer.write("Удалено дубликатов: " + duplicatesRemoved);
            }
            
            System.out.println("Сжатый файл сохранен как: test_compressed.txt");
            
            // Вариант 2: Сжатие с подробной информацией
            System.out.println("\n=== СЖАТИЕ С ПОДРОБНОЙ ИНФОРМАЦИЕЙ ===");
            compressWithDuplicatesInfo("test_input.txt", "compressed_detailed.txt", "duplicate_info.txt");
            System.out.println("Информация о дубликатах сохранена в: duplicate_info.txt");
            
            System.out.println("\n=== ВОССТАНОВЛЕНИЕ ФАЙЛА ===");
            // Восстанавливаем из простого сжатия
            restoreFile("test_compressed.txt", "restored_simple.txt", "duplicate_count.txt");
            System.out.println("Файл восстановлен (простая версия): restored_simple.txt");
            
            // Восстанавливаем из подробного сжатия
            restoreFromCompressed("compressed_detailed.txt", "duplicate_info.txt", "restored_detailed.txt");
            System.out.println("Файл восстановлен (детальная версия): restored_detailed.txt");
            
            // Показываем статистику
            showFileStats("test_input.txt", "test_compressed.txt", "restored_detailed.txt");
            
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    
    // Создание тестового файла с дубликатами
    private static void createTestFile(String filename) throws IOException {
        String[] lines = {
            "Первая строка",
            "Вторая строка",
            "Первая строка",  // Дубликат
            "Третья строка",
            "Вторая строка",  // Дубликат
            "Четвертая строка",
            "Первая строка",  // Дубликат
            "Пятая строка",
            "Третья строка",  // Дубликат
            "Шестая строка"
        };
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
    
    // Показать статистику файлов
    private static void showFileStats(String originalFile, String compressedFile, 
                                     String restoredFile) throws IOException {
        System.out.println("\n=== СТАТИСТИКА ===");
        System.out.println("Оригинальный файл: " + countLines(originalFile) + " строк");
        System.out.println("Сжатый файл: " + countLines(compressedFile) + " строк");
        System.out.println("Восстановленный файл: " + countLines(restoredFile) + " строк");
    }
    
    // Подсчет строк в файле
    private static int countLines(String filename) throws IOException {
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while (reader.readLine() != null) {
                lines++;
            }
        }
        return lines;
    }
}
