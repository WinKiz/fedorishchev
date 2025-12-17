import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.io.File;
import java.util.Stack;

public class XMLParser extends JFrame {
    private JTree xmlTree;
    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;

    public XMLParser() {
        setTitle("XML Parser");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        createMenuBar();
        createTreeView();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        
        JMenuItem openItem = new JMenuItem("Открыть XML");
        openItem.addActionListener(e -> openXMLFile());
        
        JMenuItem exitItem = new JMenuItem("Выход");
        exitItem.addActionListener(e -> System.exit(0));
        
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private void createTreeView() {
        rootNode = new DefaultMutableTreeNode("XML Документ");
        treeModel = new DefaultTreeModel(rootNode);
        
        xmlTree = new JTree(treeModel);
        xmlTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(xmlTree);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void openXMLFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите XML файл");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("XML files", "xml"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            parseXMLFile(file);
        }
    }

    private void parseXMLFile(File file) {
        try {
            // Очищаем старое дерево
            rootNode.removeAllChildren();
            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            XMLHandler handler = new XMLHandler();
            saxParser.parse(file, handler);
            
            // Обновляем дерево
            treeModel.reload();
            
            // Разворачиваем все узлы
            expandAllNodes();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Ошибка при парсинге XML: " + e.getMessage(),
                "Ошибка",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void expandAllNodes() {
        for (int i = 0; i < xmlTree.getRowCount(); i++) {
            xmlTree.expandRow(i);
        }
    }

    // Внутренний класс для обработки XML событий
    private class XMLHandler extends DefaultHandler {
        private Stack<DefaultMutableTreeNode> nodeStack = new Stack<>();
        private StringBuilder textBuffer = new StringBuilder();

        @Override
        public void startDocument() throws SAXException {
            rootNode.setUserObject("XML Документ: загружается...");
            treeModel.reload();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            // Создаем узел для текущего элемента
            String nodeName = qName;
            DefaultMutableTreeNode currentNode = new DefaultMutableTreeNode(nodeName);
            
            if (nodeStack.isEmpty()) {
                // Если это корневой элемент
                rootNode.removeAllChildren();
                rootNode.add(currentNode);
                rootNode.setUserObject("XML Документ");
            } else {
                // Если это вложенный элемент
                nodeStack.peek().add(currentNode);
            }
            
            nodeStack.push(currentNode);
            
            // Очищаем буфер для текста
            textBuffer.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            // Добавляем символы в буфер
            textBuffer.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            // Получаем текст элемента
            String text = textBuffer.toString().trim();
            
            if (!text.isEmpty()) {
                // Если есть текст, создаем для него отдельный узел
                DefaultMutableTreeNode textNode = new DefaultMutableTreeNode("Текст: " + text);
                nodeStack.peek().add(textNode);
            }
            
            // Удаляем обработанный элемент из стека
            nodeStack.pop();
            
            // Очищаем буфер
            textBuffer.setLength(0);
        }

        @Override
        public void endDocument() throws SAXException {
            treeModel.reload();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            XMLParser parser = new XMLParser();
            parser.setVisible(true);
        });
    }
}
