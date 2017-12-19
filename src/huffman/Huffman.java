package huffman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Huffman {

    public static HashMap<Character, Integer> map = new HashMap<>();
    public static HashMap<Character, String> codesMap = new HashMap<>();
    public static PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {
        public int compare(Node node1, Node node2) {
            if (node1.getValue() < node2.getValue()) {
                return -1;
            }
            if (node1.getValue() > node2.getValue()) {
                return 1;
            }
            return 0;
        }
    });

    public static void read() {

        String fileName = "inputfile.txt";
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int ch;
            char c;
            int freq = 1;
            while ((ch = bufferedReader.read()) != -1) {
                c = (char) ch;
                if (map.containsKey(c)) {
                    freq = map.get(c) + 1;
                    map.put(c, freq);
                } else {
                    map.put(c, 1);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // map.put('`', 1);
    }

    public static void insertToHeap() {
        for (char key : map.keySet()) {
            Node node = new Node();
            node.setValue(map.get(key));
            node.setCharacter(key);
            queue.add(node);
        }

    }

    public static Node buildHuffmanTree() {
        while (queue.size() != 1) {
            Node left = queue.poll();
            Node right = queue.poll();
            Node n = new Node(left.getValue() + right.getValue(), '$', left, right);
            queue.add(n);
        }
        return queue.poll();
    }

    public static void printMap() {
        for (char key : map.keySet()) {
            System.out.println("key: " + key + " value: " + map.get(key));
        }
    }

    public static void getHuffmanCodes(Node root, String code) {
        if (root == null) {
            return;
        }

        if (root.getLeft()==null&&root.getRight()==null) {
            System.out.println(root.getCharacter() + ": " + code);
            codesMap.put(root.getCharacter(), code);
        }

        getHuffmanCodes(root.getLeft(), code + "0");
        getHuffmanCodes(root.getRight(), code + "1");
    }

    public static void saveCode() {
        String inputFile = "inputfile.txt";
        String outputFile = "output";
        FileReader fr = null;
        BufferedReader br = null;
        FileOutputStream stream = null;
        try {
            fr = new FileReader(inputFile);
            br = new BufferedReader(fr);
           stream = new FileOutputStream(outputFile);
            try {
                //saving huffman codes
                for(char key: codesMap.keySet()){
                    
                }
                //saving compressed file
                String code= new String();
                int ch;
                char c;
                while ((ch = br.read()) != -1) {
                    c = (char) ch;
                    code += codesMap.get(c);
                    if(code.length()%8==0){
                         byte[] bytes = new BigInteger(code,2).toByteArray();
                         stream.write(bytes);
                         code="";
                    }
                }
                
              if(code.length()!=0){
                  byte[] bytes = new BigInteger(code,2).toByteArray();
                         stream.write(bytes);
              }
             stream.close();
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
//    public static void printHeap() {
//        while (!queue.isEmpty()) {
//            System.out.println(queue.poll().getValue());
//        }
//    }

    public static void main(String[] args) {
        read();
        printMap();
        insertToHeap();
        Node root = buildHuffmanTree();
        getHuffmanCodes(root, "");
        saveCode();
        
        
    }
}
