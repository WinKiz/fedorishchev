import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame {
    private JTextField display;
    private double firstNumber = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    public Calculator() {
        setTitle("Калькулятор");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        display = new JTextField("0");
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "⌫", "", ""
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.charAt(0) >= '0' && command.charAt(0) <= '9') {
                handleDigit(command);
            } else if (command.equals(".")) {
                handleDecimalPoint();
            } else if (command.equals("C")) {
                clearCalculator();
            } else if (command.equals("⌫")) {
                handleBackspace();
            } else if (command.equals("=")) {
                calculateResult();
            } else {
                handleOperator(command);
            }
        }

        private void handleDigit(String digit) {
            if (startNewNumber) {
                display.setText(digit);
                startNewNumber = false;
            } else {
                display.setText(display.getText() + digit);
            }
        }

        private void handleDecimalPoint() {
            if (startNewNumber) {
                display.setText("0.");
                startNewNumber = false;
            } else if (!display.getText().contains(".")) {
                display.setText(display.getText() + ".");
            }
        }

        private void clearCalculator() {
            display.setText("0");
            firstNumber = 0;
            operator = "";
            startNewNumber = true;
        }

        private void handleBackspace() {
            String currentText = display.getText();
            if (currentText.length() > 1) {
                display.setText(currentText.substring(0, currentText.length() - 1));
            } else {
                display.setText("0");
                startNewNumber = true;
            }
        }

        private void handleOperator(String op) {
            if (!operator.isEmpty()) {
                calculateResult();
            }
            firstNumber = Double.parseDouble(display.getText());
            operator = op;
            startNewNumber = true;
        }

        private void calculateResult() {
            if (operator.isEmpty()) return;

            double secondNumber = Double.parseDouble(display.getText());
            double result = 0;

            try {
                switch (operator) {
                    case "+":
                        result = firstNumber + secondNumber;
                        break;
                    case "-":
                        result = firstNumber - secondNumber;
                        break;
                    case "*":
                        result = firstNumber * secondNumber;
                        break;
                    case "/":
                        if (secondNumber == 0) {
                            display.setText("Ошибка");
                            operator = "";
                            startNewNumber = true;
                            return;
                        }
                        result = firstNumber / secondNumber;
                        break;
                }
                
                if (result == (long) result) {
                    display.setText(String.format("%d", (long) result));
                } else {
                    display.setText(String.format("%s", result));
                }
                
            } catch (Exception ex) {
                display.setText("Ошибка");
            }
            
            operator = "";
            startNewNumber = true;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
        });
    }
}
