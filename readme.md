# 📦 Huffman Compression Tool

---

## **Tool Overview**
The Huffman Compression Tool is a **Java-based desktop application** that allows users to **compress and decompress text files** using the **Huffman Coding algorithm**. It provides a **user-friendly GUI** that displays compression results, Huffman codes, and file statistics. This tool helps visualize **how data compression works** and demonstrates the practical use of **data structures and algorithms**.

---

## **Key Features**
- 🗜 **Compress text files** efficiently  
- 🗃 **Decompress files** previously compressed  
- 📊 **View compression ratio** and file size reduction  
- 🔤 **Display Huffman codes** for each character  
- 🎨 **Interactive GUI** with progress feedback and color-coded messages  
- ✅ Supports multiple files from an `input` folder and outputs results to an `output` folder  

---

## **Purpose**
The tool is designed to help students and developers:
- Understand **Huffman Coding** and how it reduces file size  
- Learn how **binary trees** and **priority queues** are used in compression algorithms  
- Explore **file input/output operations** in Java  
- Gain experience in building **interactive GUI applications**  

---

## **How It Works (Step by Step)**

1. **File Selection**
   - User selects a text file from the `input` folder using the GUI.
   - The tool reads the content of the file to process for compression.

2. **Building Huffman Tree**
   - Counts the **frequency of each character**.  
   - Creates **Huffman nodes** for each character with frequency.  
   - Uses a **priority queue** to build the tree by repeatedly combining the two lowest-frequency nodes.

3. **Generating Huffman Codes**
   - Traverses the Huffman Tree:
     - Left edge → `0`
     - Right edge → `1`
   - Assigns a **unique binary code** for each character.

4. **Encoding the Text**
   - Replaces each character with its Huffman code.  
   - Saves the encoded string to `output/compressed.txt`.

5. **Calculating Compression Ratio**
   - Compares the **size of compressed data** with the original file size.  

6. **Decoding (Decompression)**
   - Reads the compressed file and traverses the Huffman Tree according to each bit.  
   - Converts bits back to original characters and saves to `output/decompressed.txt`.

7. **Displaying Results**
   - Shows **status messages**, **file sizes**, **compression ratio**, and **Huffman codes** in the GUI.

---

## **Java Classes and APIs Used**

| Java Class / Package | Purpose |
|---------------------|---------|
| `javax.swing.*` | GUI components like `JFrame`, `JPanel`, `JButton`, `JTextArea`, `JTabbedPane`, `JProgressBar`, `JFileChooser` |
| `java.awt.*` | Layout managers (`BorderLayout`, `FlowLayout`), fonts, colors, and component sizing |
| `java.awt.event.*` | Event handling for button clicks (`ActionListener`) |
| `java.io.*` | File handling for reading and writing text files (`File`, `IOException`) |
| `java.nio.file.*` | Modern file I/O (`Files.readString()`, `Files.writeString()`, `Paths`) |
| `java.util.*` | Data structures like `Map`, `HashMap`, `PriorityQueue`, and utility classes like `Comparator` |
| `javax.swing.border.EmptyBorder` | Add padding around panels for clean layout |
| `javax.swing.JScrollPane` | Add scrollbars to text areas for large content |
| `javax.swing.SwingUtilities` | Launch GUI on the Event Dispatch Thread safely |
| `java.lang.Thread` | Used to run compression/decompression in a separate thread to avoid freezing the GUI |

> These classes collectively allow the tool to **perform file operations, manage data structures, and display an interactive, responsive GUI**.

---

## **Conclusion**
The Huffman Compression Tool is a practical demonstration of **data structures, algorithms, and GUI programming in Java**. It allows users to **compress and decompress text files**, view Huffman codes, and analyze compression results in an interactive way.
