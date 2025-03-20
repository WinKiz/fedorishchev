
import java.util.LinkedList;


public class Queue<T> 
{
    private LinkedList<T> queue;
    
    public Queue()
    {
        queue = new LinkedList <>();
    }
    public void enqueue(T item)
    {
        queue.addLast(item);
    }
     public T dequeue()
     {
         if (queue.isEmpty())
         {
             System.out.println("Очередь пуста");
             return null;
         }
         return queue.removeFirst();
     }
     public boolean isEmpty()
     {
         return queue.isEmpty();
     }
     public static void main(String[] args)
     {
         Queue<Integer>myQueue= new Queue<>();
         myQueue.enqueue(1);
         myQueue.enqueue(2);
         myQueue.enqueue(3);
         
         
         System.out.println(myQueue.dequeue());
         System.out.println(myQueue.dequeue());
         System.out.println(myQueue.dequeue());
         System.out.println(myQueue.dequeue());
     }
}
