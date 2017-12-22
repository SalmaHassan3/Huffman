
package Huffman;

public class BinaryNode {
   private int value;
   private byte byt;
   private BinaryNode left=null;
   private BinaryNode right=null;

    public BinaryNode(int freq, BinaryNode left, BinaryNode right) {
        this.value = freq;
        this.left = left;
        this.right = right;
    }
    public BinaryNode(){
        
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setByte(byte byt) {
        this.byt = byt;
    }

   
    public byte getByte() {
        return byt;
    }

    public void setLeft(BinaryNode left) {
        this.left = left;
    }

    public void setRight(BinaryNode right) {
        this.right = right;
    }
    public BinaryNode getLeft() {
        return left;
    }

    public BinaryNode getRight() {
        return right;
    }
}
