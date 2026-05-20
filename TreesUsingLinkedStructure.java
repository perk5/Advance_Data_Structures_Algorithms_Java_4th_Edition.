import java.util.*;

public class TreesUsingLinkedStructure {

    public interface Position<E> {
        E element();
    }

    public static class DNode<E> implements Position<E> {
        private DNode<E> parent;
        private List<DNode<E>> children;
        private E element;

        public DNode(DNode<E> parent, E element) {
            this.parent = parent;
            this.element = element;
            children = new ArrayList<>();
        }

        public E element() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

    }

    public interface Trees<E> {

        int size();

        boolean isEmpty();

        Position<E> root();

        Position<E> parent(Position<E> p);

        Iterable<Position<E>> children(Position<E> p);

        Iterator<E> iterator();

        Iterable<Position<E>> positions();

        E replace(Position<E> p, E e);

    }

    public static class TreeLinkedList<E> implements Trees<E> {

        private DNode<E> root;
        protected int size;

        public TreeLinkedList() {
            root = null;
            size = 0;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public Position<E> root() {
            return root;
        }

        public Position<E> parent(Position<E> p) {
            DNode<E> node = (DNode<E>) p;

            if (node.parent == null) {
                throw new IllegalStateException("Current Position doesn't have parent Node");
            }

            return node.parent;
        }

        public Iterable<Position<E>> children(Position<E> p) {
            List<Position<E>> list = new ArrayList<>();
            DNode<E> node = (DNode<E>) p;

            for (DNode<E> c : node.children) {
                list.add(c);
            }

            return list;

        }

        public Iterable<Position<E>> positions() {

            List<Position<E>> list = new ArrayList<>();

            if (root != null) {
                preorder(root, list);
            }

            return list;
        }

        public void preorder(DNode<E> node, List<Position<E>> list) {

            list.add(node);

            for (DNode<E> c : node.children) {
                preorder(c, list);
            }
        }

        public class ElementIterator implements Iterator<E> {

            List<Position<E>> list = new ArrayList<>();
            int i = 0;

            public ElementIterator() {
                if (root != null) {
                    preorder(root, list);
                }
            }

            public boolean hasNext() {
                return i < list.size();
            }

            public E next() {
                return list.get(i++).element();
            }

        }

        public Iterator<E> iterator() {
            return new ElementIterator();
        }

        public E replace(Position<E> p, E e) {

            if (p == null) {
                throw new IllegalStateException("Can't replace null value");
            }

            DNode<E> node = (DNode<E>) p;

            E old = node.element();
            node.setElement(e);

            return old;
        }

    }

    public static void main(String args[]) {

    }

}