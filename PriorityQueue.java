import java.util.*;

public class PriorityQueue {

    public static class EmptyPriorityQueueException extends Exception {
        public EmptyPriorityQueueException(String message) {
            super(message);
        }
    }

    public static class InvalidKeyException extends Exception {
        public InvalidKeyException(String message) {
            super(message);
        }
    }

    public interface Position<E> {
        E element();
    }

    public interface PositionList<E> {

    }

    public static class DNode<E> implements Position<E> {
        DNode<E> next, prev;
        E element;

        public DNode(E element, DNode<E> next, DNode<E> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }

        public E element() {
            return element;
        }

        public DNode<E> getPrev() {
            return prev;
        }

        public DNode<E> getNext() {
            return next;
        }

    }

    public static class NodePositionList<E> implements PositionList<E> {
        DNode<E> header, trailer;

        public NodePositionList(){
            this.header = new DNode<E>(null, null, null);
            this.trailer = new DNode<E>(null, null, header);
        }
    }

    public static interface Entry<E, V> {
        E Key();

        V Value();
    }

    public interface IPriorityQueue<K, V> {
        public int size();

        public boolean isEmpty();

        public Entry<K, V> min() throws EmptyPriorityQueueException;

        public Entry<K, V> insert(K key, V value) throws InvalidKeyException;

        public Entry<K, V> removeMin() throws EmptyPriorityQueueException;

    }

    public class SortedListPriorityQueue<K, V> implements IPriorityQueue<K, V> {

        protected PositionList<Entry<K, V>> entries;
        protected Comparator<K> c;
        protected Position<Entry<K, V>> actionPos;

        protected class MyEntry<E, V> implements Entry<E, V> {

            private E key;
            private V value;

            MyEntry(E key, V value) {
                this.key = key;
                this.value = value;
            }

            public E Key() {
                return key;
            }

            public V Value() {
                return value;
            }

        }
    }

    public static void main(String args[]) {

    }
}