public class Node <T extends Comparable<T>>
{
    Node<T> left, right;
    T data;
    
    // Constructors  
    public Node(){}
    
    public Node(T data){
        this.data = data;
    }
    
    // accessors and mutators for data
    public void setData(T data){
        this.data = data;
    }
    public T getData(){
        return data;
    }

    // accessors and mutators for left Node
    public void setLeft(Node<T> left){
        this.left = left;
    }
    Node<T> getLeft(){
        return left;
    }
    
    // accessors and mutators for right node
    public void setRight(Node<T> right){
        this.right = right;
    }
    Node<T> getRight(){
        return right;
    }
    
    public int compareTo(Node<T> otherNode){
        return data.compareTo(otherNode.getData());
    }
    
    
    
    
}