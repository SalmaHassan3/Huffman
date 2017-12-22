package Huffman;

import java.util.Scanner;

public class HuffmanImplementation {

    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        System.out.println("Choose type of file:\n1:Text file\n2:Binary File");
        int x=scan.nextInt();
        if(x==1){
            TextFiles t=new TextFiles();
            t.execute();
        } else if(x==2){
            BinaryFiles b=new BinaryFiles();
            b.execute();
        } else{
            System.out.println("NO such choice");
        }

    }
}
