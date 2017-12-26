package Huffman;

import java.util.Scanner;

public class HuffmanImplementation {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        long startTime, stopTime, executionTime;
        System.out.println("Choose type of file:\n1:Text file\n2:Binary File");
        int x = scan.nextInt();
        if (x == 1) {
            TextFiles t = new TextFiles();
            System.out.println("1:compress file\n2:decompress file");
            x = scan.nextInt();
            if (x == 1) {
                startTime = System.currentTimeMillis();
                t.executeCompression();
                stopTime = System.currentTimeMillis();
                executionTime = stopTime - startTime;
                System.out.println("Execution time: " + executionTime  + " milliseconds");
            } else if (x == 2) {
                startTime = System.currentTimeMillis();
                t.executeDecompression();
                stopTime = System.currentTimeMillis();
                executionTime = stopTime - startTime;
                System.out.println("Execution time: " + executionTime  + " milliseconds");
            } else {
                System.out.println("NO such choice");
            }
        } else if (x == 2) {
            BinaryFiles b = new BinaryFiles();
            System.out.println("1:compress file\n2:decompress file");
            x = scan.nextInt();
            if (x == 1) {
                startTime = System.currentTimeMillis();
                b.executeCompression();
                stopTime = System.currentTimeMillis();
                executionTime = stopTime - startTime;
                System.out.println("Execution time: " + executionTime  + " milliseconds");
            } else if (x == 2) {
                startTime = System.currentTimeMillis();
                b.executeDecompression();
                stopTime = System.currentTimeMillis();
                executionTime = stopTime - startTime;
                System.out.println("Execution time: " + executionTime  + " milliseconds");
            } else {
                System.out.println("NO such choice");
            }
        } else {
            System.out.println("NO such choice");
        }

    }
}
