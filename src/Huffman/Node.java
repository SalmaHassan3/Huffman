/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Huffman;

/**
 *
 * @author salma
 */
public class Node {
   private int value;
   private char character;
   private Node left=null;
   private Node right=null;

    public Node(int freq, char character, Node left, Node right) {
        this.value = freq;
        this.character = character;
        this.left = left;
        this.right = right;
    }
    public Node(){
        
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

   
    public char getCharacter() {
        return character;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }
    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }
   
   
}

