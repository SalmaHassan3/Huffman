package Huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class BinaryFiles {

    public  HashMap< Byte, Integer> map = new HashMap<>();
    public  HashMap<Byte, String> codesMap = new HashMap<>();
    public  HashMap<String, Byte> codesMap2 = new HashMap<>();
    public  PriorityQueue<BinaryNode> queue = new PriorityQueue<>(new Comparator<BinaryNode>() {
        public int compare(BinaryNode node1, BinaryNode node2) {
            if (node1.getValue() < node2.getValue()) {
                return -1;
            }
            if (node1.getValue() > node2.getValue()) {
                return 1;
            }
            return 0;
        }
    });

    public  void read() {

        String fileName = "inputFile.jpg";
        File file = new File(fileName);
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
            byte fileContent[] = new byte[(int) file.length()];
            stream.read(fileContent);
            int freq = 1;
            for (byte byt : fileContent) {
                if (map.containsKey(byt)) {
                    freq = map.get(byt) + 1;
                    map.put(byt, freq);
                } else {
                    map.put(byt, 1);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void printMap(HashMap< Byte, Integer> map) {
        for (byte byt : map.keySet()) {
            System.out.println(byt + ": " + map.get(byt));
        }

    }

    public  void insertToHeap() {
        for (byte key : map.keySet()) {
            BinaryNode node = new BinaryNode();
            node.setValue(map.get(key));
            node.setByte(key);
            queue.add(node);
        }

    }

    public  BinaryNode buildHuffmanTree() {
        while (queue.size() != 1) {
            BinaryNode left = queue.poll();
            BinaryNode right = queue.poll();
            BinaryNode n = new BinaryNode(left.getValue() + right.getValue(), left, right);
            queue.add(n);
        }
        return queue.poll();
    }

    public  void printHeap(PriorityQueue<BinaryNode> queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.poll().getValue());
        }
    }

    public  void getHuffmanCodes(BinaryNode root, String code) {
        if (root == null) {
            return;
        }

        if (root.getLeft() == null && root.getRight() == null) {
            codesMap.put(root.getByte(), code);
        }

        getHuffmanCodes(root.getLeft(), code + "0");
        getHuffmanCodes(root.getRight(), code + "1");
    }

    public  int getCodeSize() {
        int size = 0;
        for (byte key : codesMap.keySet()) {
            size += codesMap.get(key).length() * map.get(key);
        }
        return size;
    }

    public  void compress() {
        String inputFile = "inputFile.jpg";
        String outputFile = "compressed";
        File file = new File(inputFile);
        File file2 = new File(outputFile);
        FileInputStream stream2 = null;
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(outputFile);
            stream2 = new FileInputStream(inputFile);
            try {
                //saving huffman codes in the header
                byte[] CodeSize = ByteBuffer.allocate(4).putInt(getCodeSize()).array();
                stream.write(CodeSize);
                byte[] mapSizeBytes = ByteBuffer.allocate(4).putInt(codesMap.size()).array();
                stream.write(mapSizeBytes);
                for (byte key : codesMap.keySet()) {
                    stream.write(key);
                    byte[] sizeBytes = ByteBuffer.allocate(4).putInt(codesMap.get(key).length()).array();
                    stream.write(sizeBytes);
                    byte[] codeBytes = codesMap.get(key).getBytes();
                    stream.write(codeBytes);
                }
                //saving compressed file
                String code = new String();
                byte fileContent[] = new byte[(int) file.length()];
                stream2.read(fileContent);
                int freq = 1;
                for (byte byt : fileContent) {
                    code += codesMap.get(byt);
                    if (code.length() % 8 == 0 && code.length() != 0) {
                        int length = code.length();
                        byte[] bytes = new byte[(length + Byte.SIZE - 1) / Byte.SIZE];
                        char character;
                        for (int i = 0; i < length; i++) {
                            if ((character = code.charAt(i)) == '1') {
                                bytes[i / Byte.SIZE] = (byte) (bytes[i / Byte.SIZE] | (0x80 >>> (i % Byte.SIZE)));
                            }
                        }
                        stream.write(bytes);
                        code = "";
                    }

                }
                if (code.length() != 0) {
                    int length = code.length();
                    byte[] bytes = new byte[(length + Byte.SIZE - 1) / Byte.SIZE];
                    char character;
                    for (int i = 0; i < length; i++) {
                        if ((character = code.charAt(i)) == '1') {
                            bytes[i / Byte.SIZE] = (byte) (bytes[i / Byte.SIZE] | (0x80 >>> (i % Byte.SIZE)));
                        }
                    }
                    stream.write(bytes);
                    code = "";
                }
                if(file.length()<file2.length()){
                    System.out.println("File can't be compressed");
                    file2.delete();
                    System.exit(0);
                }
                stream.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Unable to open file '" + outputFile + "'");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + inputFile + "'");
        }
    }

    public  void decompress() {
        String inputFile = "compressed";
        String outputFile = "decompressed.jpg";
        File file = new File(inputFile);
        FileInputStream stream = null;
        FileOutputStream stream2 = null;
        int i, j, m, size, n, y;
        byte c = 0;
        try {
            stream = new FileInputStream(inputFile);
            stream2 = new FileOutputStream(outputFile);
            byte fileContent[] = new byte[(int) file.length()];
            stream.read(fileContent);
            stream.close();
            String s = new String();
            int sizeOfCode, sizeOfMap;
            for (i = 0; i < 4; i++) {
                s += String.format("%02x", fileContent[i]);
            }
            sizeOfCode = Integer.parseInt(s, 16);
            s = "";
            for (j = i; j < i + 4; j++) {
                s += String.format("%02x", fileContent[j]);
            }
            sizeOfMap = Integer.parseInt(s, 16);
            s = "";
            int count = j;
            for (int k = 0; k < sizeOfMap; k++) {
                c = fileContent[count];
                count++;
                for (m = count; m < count + 4; m++) {
                    s += String.format("%02x", fileContent[m]);
                }
                size = Integer.parseInt(s, 16);
                s = "";
                String code = new String();
                for (n = m; n < m + size; n++) {
                    code += (char) fileContent[n];
                }
                codesMap2.put(code, c);
                count = n;
            }
            s = "";
            try {
                String decode = "";
                int index = 0, taken = 0;
                for (y = count; y < fileContent.length; y++) {
                    s += String.format("%8s", Integer.toBinaryString(fileContent[y] & 0xFF)).replace(' ', '0');
                    for (int z = 0; z < s.length(); z++) {
                        decode += s.charAt(z);
                        if (codesMap2.containsKey(decode)) {
                            taken += decode.length();
                            if (taken <= sizeOfCode) {
                                stream2.write(codesMap2.get(decode));
                                decode = "";
                                index = z;
                            }
                        }
                    }
                    if (index != 0) {
                        s = s.substring(index + 1, s.length());
                        index = 0;
                    }
                    decode = "";
                }
                stream2.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Unable to open file '" + outputFile + "'");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + inputFile + "'");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void executeCompression() {
        read();
        insertToHeap();
        BinaryNode root = buildHuffmanTree();
        getHuffmanCodes(root, "");
        compress();
    }
    public void executeDecompression() {
        decompress();
    }
}
