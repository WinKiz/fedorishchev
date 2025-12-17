import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;

public class TextEditor extends JFrame {
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private File currentFile;

    public TextEditor() {
        setTitle("Текстовый редактор");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        createMenuBar();
        createTextArea();
        
        fileChooser = new JFileChooser();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Меню Файл
        JMenu fileMenu = new JMenu("Файл");
        
        JMenuItem newItem = new JMenuItem("Новый");
        JMenuItem openItem = new JMenuItem("Открыть");
        JMenuItem saveItem = new JMenuItem("Сохранить");
        JMenuItem saveAsItem = new JMenuItem("Сохранить как");
        JMenuItem exitItem = new JMenuItem("Выход");
        
        newItem.addActionListener(e -> newFile());
        openItem.addActionListener(e -> openFile());
        saveItem.addActionListener(e -> saveFile());
        saveAsItem.addActionListener(e -> saveFileAs());
        exitItem.addActionListener(e -> System.exit(0));
        
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Меню Правка
        JMenu editMenu = new JMenu("Правка");
        
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

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        
        setJMenuBar(menuBar);
    }

    private void createTextArea() {
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        add(scrollPane, BorderLayout.CENTER);
    }

    private void newFile() {
        if (textArea.getText().isEmpty() || confirmSave()) {
            textArea.setText("");
            currentFile = null;
            setTitle("Текстовый редактор - Новый файл");
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
            setTitle("Текстовый редактор - " + file.getName());
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
            setTitle("Текстовый редактор - " + file.getName());
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TextEditor editor = new TextEditor();
            editor.setVisible(true);
        });
    }
}
