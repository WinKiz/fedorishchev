

class Node {
    int data;
    Node next;


    Node(int data) {
        this.data = data;
        this.next = null;
    }
}


class LinkedList {
    Node head;

    
    LinkedList() {
        this.head = null;
    }

    
    void addNode(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node currentNode = head;
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = newNode;
        }
    }


    void printList() {
        Node currentNode = head;
        while (currentNode != null) {
            System.out.println(currentNode.data);
            currentNode = currentNode.next;
        }
    }

    
    public static void main(String[] args) {
        LinkedList list = new LinkedList();

        list.addNode(1);
        list.addNode(2);
        list.addNode(3);

        list.printList();
    }
}