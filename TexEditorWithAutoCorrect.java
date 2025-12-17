import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditorWithAutoCorrect extends JFrame {
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private File currentFile;
    private ScheduledExecutorService scheduler;
    private Timer correctionTimer;
    private Map<String, String> corrections;
    private boolean isProcessing = false;
    private int lastCaretPosition = 0;

    public TextEditorWithAutoCorrect() {
        setTitle("Текстовый редактор с автозаменой");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        initializeCorrections();
        createMenuBar();
        createTextArea();
        setupAutoCorrect();
        
        fileChooser = new JFileChooser();
    }

    private void initializeCorrections() {
        corrections = new HashMap<>();
        // Частые опечатки и их исправления
        corrections.put("првиет", "привет");
        corrections.put("првет", "привет");
        corrections.put("здраствуйте", "здравствуйте");
        corrections.put("здраствуй", "здравствуй");
        corrections.put("пака", "пока");
        corrections.put("извеняюсь", "извиняюсь");
        corrections.put("сспасибо", "спасибо");
        corrections.put("спсибо", "спасибо");
        corrections.put("агромное", "огромное");
        corrections.put("севодня", "сегодня");
        corrections.put("вагзал", "вокзал");
        corrections.put("вакзал", "вокзал");
        corrections.put("директор", "директор");
        corrections.put("превет", "привет");
        corrections.put("кстате", "кстати");
        corrections.put("вообщем", "в общем");
        corrections.put("вообще", "вообще");
        corrections.put("щас", "сейчас");
        // Добавьте свои исправления здесь
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Файл");
        JMenu editMenu = new JMenu("Правка");
        JMenu toolsMenu = new JMenu("Инструменты");

        JMenuItem newItem = new JMenuItem("Новый");
        JMenuItem openItem = new JMenuItem("Открыть");
        JMenuItem saveItem = new JMenuItem("Сохранить");
        JMenuItem saveAsItem = new JMenuItem("Сохранить как");
        JMenuItem exitItem = new JMenuItem("Выход");
        
        newItem.addActionListener(e -> newFile());
        openItem.addActionListener(e -> openFile());
        saveItem.addActionListener(e -> saveFile());
        saveAsItem.addActionListener(e -> saveFileAs());
        exitItem.addActionListener(e -> {
            shutdownScheduler();
            System.exit(0);
        });
        
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenuItem cutItem = new JMenuItem("Вырезать");
        JMenuItem copyItem = new JMenuItem("Копировать");
        JMenuItem pasteItem = new JMenuItem("Вставить");
        JMenuItem selectAllItem = new JMenuItem("Выделить все");
        
        cutItem.addActionListener(e -> textArea.cut());
        copyItem.addActionListener(e -> textArea.copy());
        pasteItem.addActionListener(e -> textArea.paste());
        selectAllItem.addActionListener(e -> textArea.selectAll());
        
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.addSeparator();
        editMenu.add(selectAllItem);

        JMenuItem autoCorrectItem = new JMenuItem("Настройки автозамены");
        autoCorrectItem.addActionListener(e -> showAutoCorrectSettings());
        
        JMenuItem showCorrectionsItem = new JMenuItem("Показать замены");
        showCorrectionsItem.addActionListener(e -> showCorrectionsList());
        
        toolsMenu.add(autoCorrectItem);
        toolsMenu.add(showCorrectionsItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(toolsMenu);
        
        setJMenuBar(menuBar);
    }

    private void createTextArea() {
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        // Добавляем слушатель для пробела
        textArea.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE) {
                    triggerAutoCorrect();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        add(scrollPane, BorderLayout.CENTER);

        // Создаем панель статуса
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEtchedBorder());
        
        JLabel statusLabel = new JLabel(" Автозамена: активна (пробел или каждые 3 сек) ");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JButton manualCorrectBtn = new JButton("Проверить сейчас");
        manualCorrectBtn.addActionListener(e -> triggerAutoCorrect());
        manualCorrectBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        
        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusPanel.add(manualCorrectBtn, BorderLayout.EAST);
        
        add(statusPanel, BorderLayout.SOUTH);
    }

    private void setupAutoCorrect() {
        // Создаем таймер для периодической проверки
        correctionTimer = new Timer(3000, e -> triggerAutoCorrect());
        correctionTimer.setRepeats(true);
        correctionTimer.start();

        // Альтернатива: ScheduledExecutorService
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::performAutoCorrect, 3, 3, TimeUnit.SECONDS);
    }

    private void triggerAutoCorrect() {
        if (!isProcessing) {
            SwingUtilities.invokeLater(this::performAutoCorrect);
        }
    }

    private void performAutoCorrect() {
        if (isProcessing || textArea.getText().isEmpty()) {
            return;
        }

        isProcessing = true;
        lastCaretPosition = textArea.getCaretPosition();

        // Запускаем в отдельном потоке
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            private String originalText;
            private String correctedText;
            private int changesMade = 0;

            @Override
            protected Void doInBackground() throws Exception {
                originalText = textArea.getText();
                correctedText = originalText;
                
                // Применяем все исправления
                for (Map.Entry<String, String> entry : corrections.entrySet()) {
                    String wrong = entry.getKey();
                    String correct = entry.getValue();
                    
                    // Используем регулярные выражения для поиска слов целиком
                    Pattern pattern = Pattern.compile("\\b" + Pattern.quote(wrong) + "\\b", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(correctedText);
                    
                    while (matcher.find()) {
                        changesMade++;
                    }
                    correctedText = matcher.replaceAll(correct);
                }
                
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    
                    if (!originalText.equals(correctedText)) {
                        // Сохраняем положение курсора
                        int caretPos = Math.min(lastCaretPosition, correctedText.length());
                        
                        // Применяем изменения в EDT
                        SwingUtilities.invokeLater(() -> {
                            textArea.setText(correctedText);
                            textArea.setCaretPosition(caretPos);
                            
                            if (changesMade > 0) {
                                showNotification("Исправлено слов: " + changesMade);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    isProcessing = false;
                }
            }
        };

        worker.execute();
    }

    private void showNotification(String message) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, message, "Автозамена", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void newFile() {
        if (textArea.getText().isEmpty() || confirmSave()) {
            textArea.setText("");
            currentFile = null;
            setTitle("Текстовый редактор с автозаменой - Новый файл");
        }
    }

    private void openFile() {
        if (!textArea.getText().isEmpty() && !confirmSave()) {
            return;
        }
        
        fileChooser.setDialogTitle("Открыть файл");
        int result = fileChooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            loadFile(currentFile);
        }
    }

    private void loadFile(File file) {
        try {
            String content = Files.readString(file.toPath());
            textArea.setText(content);
            setTitle("Текстовый редактор с автозаменой - " + file.getName());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Ошибка при чтении файла: " + e.getMessage(),
                "Ошибка",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFile() {
        if (currentFile == null) {
            saveFileAs();
        } else {
            saveToFile(currentFile);
        }
    }

    private void saveFileAs() {
        fileChooser.setDialogTitle("Сохранить файл");
        int result = fileChooser.showSaveDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            currentFile = file;
            saveToFile(file);
        }
    }

    private void saveToFile(File file) {
        try {
            String text = textArea.getText();
            Files.writeString(file.toPath(), text);
            setTitle("Текстовый редактор с автозаменой - " + file.getName());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Ошибка при сохранении файла: " + e.getMessage(),
                "Ошибка",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean confirmSave() {
        if (textArea.getText().isEmpty()) {
            return true;
        }
        
        int result = JOptionPane.showConfirmDialog(this,
            "Сохранить изменения в текущем файле?",
            "Сохранение",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (result == JOptionPane.YES_OPTION) {
            saveFile();
            return true;
        } else if (result == JOptionPane.NO_OPTION) {
            return true;
        }
        
        return false;
    }

    private void showAutoCorrectSettings() {
        JDialog settingsDialog = new JDialog(this, "Настройки автозамены", true);
        settingsDialog.setSize(400, 300);
        settingsDialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Map.Entry<String, String> entry : corrections.entrySet()) {
            listModel.addElement(entry.getKey() + " → " + entry.getValue());
        }
        
        JList<String> correctionsList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(correctionsList);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Добавить");
        JButton removeButton = new JButton("Удалить");
        
        addButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(settingsDialog, 
                "Введите пару: неправильное слово → правильное слово");
            if (input != null && input.contains("→")) {
                String[] parts = input.split("→");
                if (parts.length == 2) {
                    String wrong = parts[0].trim();
                    String correct = parts[1].trim();
                    corrections.put(wrong, correct);
                    listModel.addElement(wrong + " → " + correct);
                }
            }
        });
        
        removeButton.addActionListener(e -> {
            int selectedIndex = correctionsList.getSelectedIndex();
            if (selectedIndex != -1) {
                String selected = listModel.get(selectedIndex);
                String wrong = selected.split("→")[0].trim();
                corrections.remove(wrong);
                listModel.remove(selectedIndex);
            }
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        
        panel.add(new JLabel("Список автозамен:"), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        settingsDialog.add(panel);
        settingsDialog.setVisible(true);
    }

    private void showCorrectionsList() {
        StringBuilder sb = new StringBuilder();
        sb.append("Текущие правила автозамены:\n\n");
        
        for (Map.Entry<String, String> entry : corrections.entrySet()) {
            sb.append(entry.getKey()).append(" → ").append(entry.getValue()).append("\n");
        }
        
        JOptionPane.showMessageDialog(this, sb.toString(), "Правила автозамены", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void shutdownScheduler() {
        if (correctionTimer != null) {
            correctionTimer.stop();
        }
        if (scheduler != null) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
            }
        }
    }

    @Override
    public void dispose() {
        shutdownScheduler();
        super.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TextEditorWithAutoCorrect editor = new TextEditorWithAutoCorrect();
            editor.setVisible(true);
        });
    }
}
