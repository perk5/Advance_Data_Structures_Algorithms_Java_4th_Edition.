public class StackDll {

    static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        Node(E data) {
            this.data = data;
        }
    }

    static class DoubleDll<E> {

        Node<E> head;
        Node<E> tail;

        public void push(E data) {
            Node<E> n = new Node<>(data);

            if (head == null) {
                head = n;
                tail = n;
                return;
            }
            head.prev = n;
            n.next = head;
            head = n;
        }

        public void pop(){

            if(head == null){
                System.out.println("Stack is empty...");
                return;
            }

            head = head.next;

            if(head != null){
                head.prev = null; 
            }

            if(head == null){
                tail = null;
            }
        }

        public void top(){

            if(head != null){
                System.out.println(head.data);
                return;
            }else{
                return;
            }
             
        }

        public void traverse() {

            Node<E> current = head;

            if (current == null) {
                System.out.println("Stack is Empty..");
                return;
            }

            StringBuilder s = new StringBuilder();
            while (current != null) {
                s.append(current.data);

                if (current.next != null)
                    s.append("-->");

                current = current.next;
            }
            System.out.println(s);
        }

        public void reverseTraverse() {

            Node<E> current = tail;

            if (current == null) {
                System.out.println("Stack is Empty..");
                return;
            }

            StringBuilder s = new StringBuilder();
            while (current != null) {
                s.append(current.data);

                if (current.prev != null)
                    s.append("-->");

                current = current.prev;
            }
            System.out.println(s);
        }

    }

    public static void main(String args[]) {
        DoubleDll<Integer> dll = new DoubleDll<>();
        
        dll.push(1);
        dll.push(2);
        // dll.push(3);
        dll.top();
        dll.traverse();
        dll.reverseTraverse();

    }

}