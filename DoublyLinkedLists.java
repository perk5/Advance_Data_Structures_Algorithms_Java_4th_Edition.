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

    public void traver(Node first) {
        Node current = first;

        if (current == null) {
            return;
        }
        System.out.println("<--" + current.name + " -->");

        if (current.next == null) {
            return;
        }

        current = current.next;

        traver(current);
    }

    public void swap() {
        Node current = head;

        Node x = current.next;
        Node y = current.next.next;

        current.next = y;
        current.next.prev = current;
        current.next.next = x;
        current.next.next.prev = y;
        current.next.next.next = y.next;
    }

    public int recursiveCountNodes(Node nodes) {
        if (nodes == null) {
            return 0;
        }
        return 1 + recursiveCountNodes(nodes.next);
    }

}

class DoublyLinkedLists {
    public static void main(String args[]) {
        CreateDoublyLinkedLists create = new CreateDoublyLinkedLists();
        create.insertFromHead("First");
        create.insertFromHead("Second");
        create.insertFromHead("Third");
        create.insertFromHead("Fourth");
        // create.swap();
        System.out.println(create.recursiveCountNodes(create.head));
        // create.traverse();
    }
}