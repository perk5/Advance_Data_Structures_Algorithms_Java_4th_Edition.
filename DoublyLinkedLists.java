class Node {
    String name;
    Node prev;
    Node next;

    Node(String name) {
        this.name = name;
    }
}

class CreateDoublyLinkedLists {
    Node head;
    Node tail;
    int size = 0;

    public Node insertFromHead(String name) {
        Node newNode = new Node(name);
        if (head == null) {
            // newNode.next = null;
            // newNode.prev = null;
            head = newNode;
            tail = newNode;
            size++;
            return head;
        }

        newNode.next = head;
        head.prev = newNode;
        head = newNode;

        size++;
        return head;
    }

    public Node insertFromTail(String name) {
        Node newNode = new Node(name);
        if (tail == null) {
            tail = newNode;
            head = newNode;
            size++;
            return tail;
        }
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
        size++;
        return tail;
    }

    public void traverse() {
        Node current = head;

        while (current != null) {
            System.out.print("<- " + current.name + " -> ");
            current = current.next;
        }
        System.out.println();

    }

}

class DoublyLinkedLists {
    public static void main(String args[]) {
        CreateDoublyLinkedLists create = new CreateDoublyLinkedLists();
        create.insertFromHead("First");
        create.insertFromHead("Second");
        create.insertFromTail("Zero");

        create.traverse();
    }
}