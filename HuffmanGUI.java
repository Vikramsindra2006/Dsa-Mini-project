import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

// HuffmanNode class
class HuffmanNode implements Comparable<HuffmanNode> {
    char ch;
    int freq;
    HuffmanNode left, right;

    HuffmanNode(char ch, int freq) {
        this.ch = ch;
        this.freq = freq;
        this.left = null;
        this.right = null;
    }

    @Override
    public int compareTo(HuffmanNode node) {
        return this.freq - node.freq;
    }

    public boolean isLeaf() {
        return (this.left == null && this.right == null);
    }
}

// HuffmanEncoder class
class HuffmanEncoder {
    private Map<Character, String> huffmanCode = new HashMap<>();
    private HuffmanNode root;

    public void buildTree(String text) {
        if (text == null || text.isEmpty()) return;

        Map<Character, Integer> freqMap = new HashMap<>();
        for (char ch : text.toCharArray())
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);

        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet())
            pq.add(new HuffmanNode(entry.getKey(), entry.getValue()));

        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode newNode = new HuffmanNode('\0', left.freq + right.freq);
            newNode.left = left;
            newNode.right = right;
            pq.add(newNode);
        }

        root = pq.peek();
        generateCodes(root, "");
    }

    private void generateCodes(HuffmanNode node, String code) {
        if (node == null) return;
        if (node.isLeaf())
            huffmanCode.put(node.ch, code.length() > 0 ? code : "0");

        generateCodes(node.left, code + '0');
        generateCodes(node.right, code + '1');
    }

    public Map<Character, String> getCodes() {
        return huffmanCode;
    }

    public String encode(String text) {
        StringBuilder sb = new StringBuilder();
        for (char ch : text.toCharArray())
            sb.append(huffmanCode.get(ch));
        return sb.toString();
    }

    public String decode(String encodedText) {
        if (root == null || encodedText == null || encodedText.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        HuffmanNode curr = root;
        for (char bit : encodedText.toCharArray()) {
            curr = (bit == '0') ? curr.left : curr.right;
            if (curr.isLeaf()) {
                sb.append(curr.ch);
                curr = root;
            }
        }
        return sb.toString();
    }
}

// Attractive Huffman GUI
class HuffmanGUI extends JFrame {
    private JTextArea outputArea, codeArea;
    private JButton compressBtn, decompressBtn, chooseFileBtn;
    private JLabel fileInfoLabel, ratioLabel;
    private JProgressBar progressBar;
    private File selectedFile;
    private HuffmanEncoder encoder;

    public HuffmanGUI() {
        setTitle("📦 Huffman Compression Tool");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Left panel for operations
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(30, 30, 30));
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        chooseFileBtn = createButton("📂 Choose File", new Color(52, 152, 219));
        compressBtn = createButton("🗜 Compress", new Color(46, 204, 113));
        decompressBtn = createButton("🗃 Decompress", new Color(231, 76, 60));
        leftPanel.add(chooseFileBtn);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(compressBtn);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(decompressBtn);

        // Progress bar
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(41, 128, 185));
        progressBar.setValue(0);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(progressBar);

        add(leftPanel, BorderLayout.WEST);

        // Right panel with tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel outputPanel = new JPanel(new BorderLayout(5, 5));
        outputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel codePanel = new JPanel(new BorderLayout(5, 5));
        codePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        fileInfoLabel = new JLabel("No file selected");
        fileInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ratioLabel = new JLabel("Compression Ratio: N/A");
        ratioLabel.setFont(new Font("Arial", Font.BOLD, 14));

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputArea.setBackground(new Color(245, 245, 245));
        JScrollPane scrollOut = new JScrollPane(outputArea);

        codeArea = new JTextArea();
        codeArea.setEditable(false);
        codeArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        codeArea.setBackground(new Color(250, 250, 250));
        JScrollPane scrollCode = new JScrollPane(codeArea);

        outputPanel.add(fileInfoLabel, BorderLayout.NORTH);
        outputPanel.add(scrollOut, BorderLayout.CENTER);
        outputPanel.add(ratioLabel, BorderLayout.SOUTH);

        codePanel.add(scrollCode, BorderLayout.CENTER);

        tabbedPane.addTab("Output", outputPanel);
        tabbedPane.addTab("Huffman Codes", codePanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Button actions
        chooseFileBtn.addActionListener(e -> chooseFile());
        compressBtn.addActionListener(e -> compressFile());
        decompressBtn.addActionListener(e -> decompressFile());
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser("./input");
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            fileInfoLabel.setText("Selected file: " + selectedFile.getName() +
                    " (Size: " + selectedFile.length() + " bytes)");
            outputArea.setText("");
            codeArea.setText("");
            ratioLabel.setText("Compression Ratio: N/A");
        }
    }

    private void compressFile() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Please select a file first!");
            return;
        }

        new Thread(() -> {
            try {
                progressBar.setValue(0);
                String text = Files.readString(selectedFile.toPath());
                encoder = new HuffmanEncoder();
                encoder.buildTree(text);

                progressBar.setValue(30);
                String encoded = encoder.encode(text);

                Path outputPath = Path.of("output/compressed.txt");
                Files.createDirectories(outputPath.getParent());
                Files.writeString(outputPath, encoded);

                progressBar.setValue(70);
                outputArea.setForeground(new Color(0, 128, 0));
                outputArea.setText("✅ File compressed successfully!\n\n");
                outputArea.append("Compressed size: " + encoded.length() + " bits\n");
                codeArea.setText(encoder.getCodes().toString());

                double ratio = (double) encoded.length() / (text.length() * 8) * 100;
                ratioLabel.setText(String.format("Compression Ratio: %.2f%%", ratio));
                progressBar.setValue(100);
            } catch (IOException ex) {
                outputArea.setForeground(Color.RED);
                outputArea.setText("❌ Error: " + ex.getMessage());
            }
        }).start();
    }

    private void decompressFile() {
        if (encoder == null) {
            JOptionPane.showMessageDialog(this, "Please compress a file first!");
            return;
        }

        new Thread(() -> {
            try {
                progressBar.setValue(0);
                String encoded = Files.readString(Path.of("output/compressed.txt"));
                String decoded = encoder.decode(encoded);

                Path outputPath = Path.of("output/decompressed.txt");
                Files.writeString(outputPath, decoded);

                outputArea.setForeground(new Color(0, 128, 255));
                outputArea.append("\n✅ File decompressed successfully!\n");
                outputArea.append("Output saved to: " + outputPath.toAbsolutePath() + "\n");
                progressBar.setValue(100);
            } catch (IOException ex) {
                outputArea.setForeground(Color.RED);
                outputArea.append("\n❌ Error during decompression: " + ex.getMessage());
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HuffmanGUI().setVisible(true));
    }
}
