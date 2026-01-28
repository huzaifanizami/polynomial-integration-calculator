import java.util.*;
import java.io.*;


public class BinTree <T extends Comparable<T>>
{
    Node<T> root = null;
    
    // accessors and mutators for ROOT
    void setRoot(Node<T> root){
        this.root = root;
    }
    
    Node<T> getRoot(){
        return root;
    }
    
    public int gcd(int a, int b) {
        if (b==0) return a;
        return gcd(b,a%b);
    }
    
    // method to search a node from the tree
    public T search(Node<T> n) {
        return searchHelper(root, n);
    }
    
    public T searchHelper(Node<T> cur, Node<T> n)
    {
        if (cur == null){
            return null;    
        }
        if(n.getData().compareTo(cur.getData()) == 0)
            return n.getData();
        else if(n.getData().compareTo(cur.getData()) < 0)
	        return searchHelper(cur.getLeft(), n);
        else
	        return searchHelper(cur.getRight(), n);
    }
    
    //insert a node in the tree
    public void insert(Node<T> n){
        if (root == null){
            root = n;
        }
        else{
            insertHelper(root, n);
        }
    }
    public void insertHelper(Node<T> cur, Node<T> n)
    {
        // depending on the comparison, the place to insert the new node in the tree is determined 
        if (n.compareTo(cur) < 0) {
            if (cur.getLeft() == null) {
                cur.setLeft(n);
            } 
            else {
                insertHelper(cur.getLeft(), n);
            }
        } 
        else if (n.compareTo(cur) > 0)
        {
            if (cur.getRight() == null) {
                cur.setRight(n);
            } 
            else {
                insertHelper(cur.getRight(), n);
            }
        }
        else{
            equalNodeInsert(cur, n);
        }
        
    }
    
    // if a term with an exponent that is already present in the tree is encountered, it add the both terms
    public void equalNodeInsert(Node cur, Node n){
        Term term1 = new Term();
        Term term2 = new Term();
        term1 = (Term) cur.getData();
        term2 = (Term) n.getData();
    
        //denominator comparisons to determine the need for lcm
        if (term1.getCoefficientDenom() == term2.getCoefficientDenom()){
            ((Term)cur.getData()).setCoefficient(term1.getCoefficient() +
            term2.getCoefficient());
        }
        else{
            int denomSame = term2.getCoefficientDenom() *
                            term1.getCoefficientDenom();
            
            term1.setCoefficient(term2.getCoefficientDenom()*term1.getCoefficient() +
                                term2.getCoefficient()*term1.getCoefficientDenom());
            
            term1.setCoefficientDenom(denomSame);
            
            cur.setData(term1);
            
        }
    }
    
    // treversing through the tree in an ascending order returning the terms 
    public void inOrderTraversal(ArrayList<T> result) {
        inOrderTraversalHelper(root, result);
    }
    private void inOrderTraversalHelper(Node<T> node, ArrayList<T> result) {
        if (node == null) {
            return;
        }
        
        inOrderTraversalHelper(node.getLeft(), result);
        result.add(node.getData());
        inOrderTraversalHelper(node.getRight(), result);
    }
    
}

