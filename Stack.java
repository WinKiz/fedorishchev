
package stack;


class Node
{
int data;
Node next;

public Node(int data) 
{
    this.data = data;
    this.next = null;
}
}

public class Stack
{
private Node top;

public Stack() 
{
    this.top = null;
}


public void push(int item) 
{
    Node newNode = new Node(item); 
    newNode.next = top; 
    top = newNode; 
}

public Integer pop() 
{
    if (isEmpty()) 
    {
        System.out.println("Stack is empty!");
        return null;
    } 
    else 
       {
        int poppedValue = top.data; 
        top = top.next; 
        return poppedValue; 
       }
}


public boolean isEmpty() 
{
    return top == null; 
}


public void displayStack() 
{
    Node current = top;
    while (current != null) 
    {
        System.out.println(current.data); 
        current = current.next; 
    }
}


public static void main(String[] args) 
{
    Stack stack = new Stack();
    stack.push(1);
    stack.push(2);
    stack.push(3);
    System.out.println("Stack:");
    stack.displayStack();
    System.out.println("Deleted element: " + stack.pop());
    System.out.println("Stack:");
    stack.displayStack();
}
}