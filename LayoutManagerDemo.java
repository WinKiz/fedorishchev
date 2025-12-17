import javax.swing.*;
import java.awt.*;

public class LayoutManagerDemo extends JFrame {
    
    public LayoutManagerDemo() {
        setTitle("Демонстрация менеджеров компоновки");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        
        // Основной контейнер с BorderLayout
        Container mainContainer = getContentPane();
        mainContainer.setLayout(new BorderLayout(10, 10));
        
        // 1. Верхняя панель с FlowLayout - меню
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBackground(new Color(220, 240, 255));
        
        // Добавляем кнопки-меню
        String[] menuItems = {"Файл", "Правка", "Вид", "Инструменты", "Справка"};
        for (String item : menuItems) {
            JButton menuButton = new JButton(item);
            menuButton.setPreferredSize(new Dimension(80, 30));
            topPanel.add(menuButton);
        }
        
        // Добавляем верхнюю панель в основное окно
        mainContainer.add(topPanel, BorderLayout.NORTH);
        
        // 2. Центральная панель с FlowLayout для отображения элементов
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        centerPanel.setBackground(new Color(255, 250, 240));
        
        // Добавляем разноцветные блоки
        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.PINK};
        for (int i = 0; i < 6; i++) {
            JPanel colorBlock = createColorBlock(colors[i], "Блок " + (i + 1));
            centerPanel.add(colorBlock);
        }
        
        // Добавляем центральную панель в основное окно
        mainContainer.add(centerPanel, BorderLayout.CENTER);
        
        // 3. Левая панель с FlowLayout для инструментов
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        leftPanel.setBackground(new Color(240, 255, 240));
        leftPanel.setPreferredSize(new Dimension(150, 0));
        
        // Добавляем кнопки инструментов
        String[] tools = {"✏️ Карандаш", "🟦 Кисть", "✏️ Линия", "⬜ Прямоугольник", "🔴 Овал", "🔺 Треугольник"};
        for (String tool : tools) {
            JButton toolButton = new JButton(tool);
            toolButton.setPreferredSize(new Dimension(130, 40));
            leftPanel.add(toolButton);
        }
        
        // Добавляем левую панель в основное окно
        mainContainer.add(leftPanel, BorderLayout.WEST);
        
        // 4. Правая панель с FlowLayout для свойств
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        rightPanel.setBackground(new Color(255, 240, 255));
        rightPanel.setPreferredSize(new Dimension(150, 0));
        
        // Добавляем элементы управления свойствами
        JLabel propertyLabel = new JLabel("Свойства:");
        propertyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        rightPanel.add(propertyLabel);
        
        JSlider thicknessSlider = new JSlider(1, 10, 3);
        thicknessSlider.setPreferredSize(new Dimension(130, 40));
        thicknessSlider.setMajorTickSpacing(3);
        thicknessSlider.setPaintTicks(true);
        thicknessSlider.setPaintLabels(true);
        rightPanel.add(thicknessSlider);
        
        JComboBox<String> colorCombo = new JComboBox<>(new String[]{"Черный", "Красный", "Зеленый", "Синий"});
        colorCombo.setPreferredSize(new Dimension(130, 30));
        rightPanel.add(colorCombo);
        
        JCheckBox fillCheck = new JCheckBox("Заливка");
        fillCheck.setPreferredSize(new Dimension(130, 30));
        rightPanel.add(fillCheck);
        
        // Добавляем правую панель в основное окно
        mainContainer.add(rightPanel, BorderLayout.EAST);
        
        // 5. Нижняя панель с FlowLayout для статуса
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        bottomPanel.setBackground(new Color(240, 240, 240));
        
        // Добавляем элементы статусной строки
        JLabel statusLabel = new JLabel("Статус:");
        bottomPanel.add(statusLabel);
        
        JLabel coordinatesLabel = new JLabel("Координаты: X=0, Y=0");
        coordinatesLabel.setPreferredSize(new Dimension(150, 20));
        bottomPanel.add(coordinatesLabel);
        
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(75);
        progressBar.setPreferredSize(new Dimension(200, 20));
        progressBar.setString("Загрузка: 75%");
        progressBar.setStringPainted(true);
        bottomPanel.add(progressBar);
        
        // Добавляем нижнюю панель в основное окно
        mainContainer.add(bottomPanel, BorderLayout.SOUTH);
        
        // Панель с другим менеджером компоновки внутри центральной панели
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BorderLayout(5, 5));
        innerPanel.setBackground(new Color(230, 230, 250));
        innerPanel.setBorder(BorderFactory.createTitledBorder("Внутренняя панель с BorderLayout"));
        innerPanel.setPreferredSize(new Dimension(250, 150));
        
        // Добавляем компоненты во внутреннюю панель с BorderLayout
        JButton northBtn = new JButton("Север");
        northBtn.setBackground(new Color(200, 220, 255));
        innerPanel.add(northBtn, BorderLayout.NORTH);
        
        JButton southBtn = new JButton("Юг");
        southBtn.setBackground(new Color(200, 220, 255));
        innerPanel.add(southBtn, BorderLayout.SOUTH);
        
        JButton westBtn = new JButton("Запад");
        westBtn.setBackground(new Color(255, 220, 200));
        innerPanel.add(westBtn, BorderLayout.WEST);
        
        JButton eastBtn = new JButton("Восток");
        eastBtn.setBackground(new Color(255, 220, 200));
        innerPanel.add(eastBtn, BorderLayout.EAST);
        
        JTextArea centerText = new JTextArea("Центральная область\nBorderLayout демонстрация");
        centerText.setBackground(new Color(255, 255, 200));
        centerText.setEditable(false);
        innerPanel.add(new JScrollPane(centerText), BorderLayout.CENTER);
        
        // Добавляем внутреннюю панель в центральную панель
        centerPanel.add(innerPanel);
        
        // Информационная метка
        JLabel infoLabel = new JLabel(
            "<html><center>Демонстрация менеджеров компоновки:<br>" +
            "• Основное окно: BorderLayout<br>" +
            "• Все внутренние панели: FlowLayout<br>" +
            "• Самая внутренняя панель: BorderLayout</center></html>"
        );
        infoLabel.setFont(new Font("Arial", Font.BOLD, 12));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainContainer.add(infoLabel, BorderLayout.PAGE_START);
    }
    
    private JPanel createColorBlock(Color color, String label) {
        JPanel block = new JPanel();
        block.setLayout(new BorderLayout());
        block.setPreferredSize(new Dimension(100, 100));
        block.setBackground(color);
        block.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        JLabel textLabel = new JLabel(label, SwingConstants.CENTER);
        textLabel.setForeground(Color.BLACK);
        textLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        block.add(textLabel, BorderLayout.SOUTH);
        
        return block;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LayoutManagerDemo demo = new LayoutManagerDemo();
            demo.setVisible(true);
        });
    }
}
