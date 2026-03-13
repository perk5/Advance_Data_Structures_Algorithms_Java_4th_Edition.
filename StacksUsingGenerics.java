import org.w3c.dom.Node;

interface Declaration<E> {
    int size();

    void addAtTop(E data);

    void pop();
}

public class StacksUsingGenerics {

    public class EmptyStackException extends RuntimeException {
        public EmptyStackException(String err) {
            super(err);
        }
    }

    public class Node<E> {
        E data;
        Node<E> next;

        public Node(E data) {
            this.data = data;
        }
    }

    public class StackImplementation<E> implements Declaration<E> {

        private Node<E> t;
        private Node<E> b;
        private Node<E> bLast;

        private int count;

        public void addAtTop(E data) {

            Node<E> newNode = new Node<>(data);

            newNode.next = t;
            t = newNode;

            count++;

        }

        public void pop() {
            if (t == null) {
                throw new EmptyStackException("Stack is Empty..");
            }

            
            Node<E> newNode = new Node<>(t.data);

            if (b == null) {
                b = newNode;
                bLast = b; 
            } else {
                bLast.next = newNode;
                bLast = bLast.next; 
            }

            
            t = t.next;
            count--;
        }

        public int size() {
            return count;
        }

        public void traverse() {
            Node<E> top = t;
            String display = "";
            while (top != null) {
                display = top.data + " --> " + display;
                top = top.next;
            }

            System.out.println(display + "null");
        }

        public void reverse() {
            Node<E> bottom = b;
            String display = "";
            while (bottom != null) {
                display = bottom.data + " --> " + display;
                bottom = bottom.next;
            }

            System.out.println(display + "null");
        }

    }

    public static void main(String args[]) {
        StacksUsingGenerics outer = new StacksUsingGenerics();
        StackImplementation<Integer> SI = outer.new StackImplementation<>();
        SI.addAtTop(3);
        SI.addAtTop(2);
        SI.addAtTop(1);
        SI.pop();
        SI.pop();
        SI.pop();

        SI.traverse();
        SI.reverse();
    }
}