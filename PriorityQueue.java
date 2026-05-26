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

    // Exceptions

    public static class EmptyTreeException extends Exception {
        public EmptyTreeException(String message) {
            super(message);
        }
    }

    public static class InvalidPositionException extends Exception {
        public InvalidPositionException(String message) {
            super(message);
        }
    }

    public static class BoundaryViolationException extends Exception {
        public BoundaryViolationException(String message) {
            super(message);
        }
    }

    public static class EmptyListException extends Exception {
        public EmptyListException(String message) {
            super(message);
        }
    }

    public interface Position<E> {
        E element();
    }

    public interface PositionList<E> extends Iterable<E> {
        public Iterator<E> iterator();

        public int size();

        public boolean isEmpty();

        public Position<E> first() throws EmptyListException;

        public Position<E> last() throws EmptyListException;

        public Position<E> next(Position<E> P) throws InvalidPositionException, BoundaryViolationException;

        public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException;

        public void addFirst(E e);

        public void addLast(E e);

        public void addAfter(Position<E> p, E e) throws InvalidPositionException;

        public void addBefore(Position<E> p, E e)
                throws InvalidPositionException, EmptyListException, BoundaryViolationException;

        public E remove(Position<E> p) throws InvalidPositionException;

        public E set(Position<E> p, E e) throws InvalidPositionException;

        public Iterable<Position<E>> positions()
                throws EmptyListException, InvalidPositionException, BoundaryViolationException;
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

        public void setNext(DNode<E> newNode) {
            this.next = newNode;
        }

        public void setPrev(DNode<E> newNode) {
            this.prev = newNode;
        }

        public void setElement(E e) {
            this.element = e;
        }

    }

    public static class NodePositionList<E> implements PositionList<E> {
        DNode<E> header, trailer;
        int size;

        public NodePositionList() {
            this.header = new DNode<E>(null, null, null);
            this.trailer = new DNode<E>(null, null, header);
            header.next = trailer;
        }

        protected DNode<E> checkPosition(Position<E> p) throws InvalidPositionException {
            if (p == null) {
                throw new InvalidPositionException("Null position passed to NodeList");
            }
            if (p == header) {
                throw new InvalidPositionException("The header is not a valid position");
            }
            if (p == trailer) {
                throw new InvalidPositionException("The trailer is not a valid position");
            }

            try {
                DNode<E> temp = (DNode<E>) p;
                if ((temp.getPrev() == null) || (temp.getNext() == null)) {
                    throw new InvalidPositionException("Position does not belong to a valid NodeList");
                }

                return temp;
            } catch (ClassCastException e) {
                throw new InvalidPositionException("Position is of wrong type for this list");
            }

        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public Position<E> first() throws EmptyListException {
            if (isEmpty()) {
                throw new EmptyListException("List is Empty.");
            }
            return header.getNext();
        }

        public Position<E> last() throws EmptyListException {
            if (isEmpty()) {
                throw new EmptyListException("List is Empty.");
            }
            return trailer.getPrev();
        }

        public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
            DNode<E> currentPosition = checkPosition(p);

            return currentPosition.getNext();
        }

        public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
            DNode<E> currentPosition = checkPosition(p);

            return currentPosition.getPrev();
        }

        public void addFirst(E e) {

            DNode<E> first = header.getNext();
            DNode<E> newNode = new DNode<E>(e, first, header);

            header.setNext(newNode);
            first.setPrev(newNode);

            size++;
        }

        public void addLast(E e) {
            DNode<E> last = trailer.getPrev();
            DNode<E> newNode = new DNode<E>(e, last, trailer);

            last.setNext(newNode);
            trailer.setPrev(newNode);

            size++;
        }

        public void addAfter(Position<E> p, E e) throws InvalidPositionException {
            DNode<E> node = checkPosition(p);
            DNode<E> next = node.getNext();
            DNode<E> newNode = new DNode<E>(e, node.getNext(), node);

            node.setNext(newNode);
            next.setPrev(newNode);

            size++;

        }

        public void addBefore(Position<E> p, E e)
                throws InvalidPositionException, EmptyListException, BoundaryViolationException {
            p = checkPosition(p);

            if (p == first())
                addFirst(e);
            else
                addAfter(prev(p), e);
        }

        public E remove(Position<E> p) throws InvalidPositionException {
            DNode<E> v = checkPosition(p);
            size--;
            DNode<E> vPrev = v.getPrev();
            DNode<E> vNext = v.getNext();
            vPrev.setNext(vNext);
            vNext.setPrev(vPrev);
            E vElem = v.element();
            v.setNext(null);
            v.setPrev(null);
            return vElem;
        }

        public E set(Position<E> p, E e) throws InvalidPositionException {
            DNode<E> v = checkPosition(p);
            E oldElt = v.element();
            v.setElement(e);
            return oldElt;
        }

        public Iterable<Position<E>> positions()
                throws EmptyListException, InvalidPositionException, BoundaryViolationException {
            PositionList<Position<E>> P = new NodePositionList<Position<E>>();
            if (!isEmpty()) {
                Position<E> p = first();
                while (true) {
                    P.addLast(p);
                    if (p == last()) {
                        break;
                    }
                    p = next(p);
                }
            }
            return P;

        }

        public class ElementIterator implements Iterator<E> {
            protected PositionList<E> list;
            protected Position<E> cursor;

            public ElementIterator(PositionList<E> L) {
                list = L;

                try {
                    cursor = list.isEmpty() ? null : list.first();
                } catch (EmptyListException e) {
                    cursor = null;
                }
            }

            public boolean hasNext() {
                return cursor != null;
            }

            public E next() {
                if (cursor == null)
                    throw new NoSuchElementException("No Next Element...!");

                E toReturn;

                try {
                    toReturn = cursor.element();

                    if (cursor == list.last())
                        cursor = null;
                    else
                        cursor = list.next(cursor);

                } catch (Exception e) {
                    throw new NoSuchElementException("Iterator error");
                }
                return toReturn;
            }

        }

        public Iterator<E> iterator() {
            return new ElementIterator(this);
        }
    }

    public static interface Entry<E, V> {
        E getKey();

        V getValue();
    }

    public interface IPriorityQueue<K, V> {
        public int size();

        public boolean isEmpty();

        public Entry<K, V> min() throws EmptyPriorityQueueException, EmptyListException;

        public Entry<K, V> insert(K key, V value)
                throws InvalidKeyException, EmptyListException, InvalidPositionException, BoundaryViolationException;

        public Entry<K, V> removeMin() throws EmptyPriorityQueueException, InvalidPositionException, EmptyListException;

    }

    public static class SortedListPriorityQueue<K, V> implements IPriorityQueue<K, V> {

        protected PositionList<Entry<K, V>> entries;
        protected Comparator<K> c;
        protected Position<Entry<K, V>> actionPos;

        public SortedListPriorityQueue() {
            entries = new NodePositionList<Entry<K, V>>();
            c = new DefaultComparator<K>();
        }

        public SortedListPriorityQueue(Comparator<K> comp) {
            entries = new NodePositionList<Entry<K, V>>();
            c = comp;
        }

        public class DefaultComparator<E> implements Comparator<E> {
            public int compare(E a, E b) throws ClassCastException {
                return ((Comparable<E>) a).compareTo(b);
            }
        }

        private void checkKey(K k) throws InvalidKeyException {
            try {
                c.compare(k, k);
            } catch (ClassCastException | NullPointerException e) {
                throw new InvalidKeyException("Key cannot be compared");
            }
        }

        protected static class MyEntry<E, V> implements Entry<E, V> {

            private E key;
            private V value;

            MyEntry(E key, V value) {
                this.key = key;
                this.value = value;
            }

            public E getKey() {
                return key;
            }

            public V getValue() {
                return value;
            }

        }

        public int size() {
            return entries.size();
        }

        public boolean isEmpty() {
            return entries.isEmpty();
        }

        public Entry<K, V> min() throws EmptyPriorityQueueException, EmptyListException {
            if (entries.isEmpty()) {
                throw new EmptyPriorityQueueException("Priority Queue is empty");
            } else {
                return entries.first().element();
            }

        }

        public Entry<K, V> insert(K key, V value) throws InvalidKeyException, EmptyListException,
                InvalidPositionException, BoundaryViolationException {
            checkKey(key);
            Entry<K, V> entry = new MyEntry<K, V>(key, value);
            insertEntry(entry);
            return entry;
        }

        public void insertEntry(Entry<K, V> e)
                throws EmptyListException, InvalidPositionException, BoundaryViolationException {
            if (entries.isEmpty()) {
                entries.addFirst(e);
                actionPos = entries.first();
            } else if (c.compare(e.getKey(), entries.last().element().getKey()) > 0) {
                entries.addLast(e);
                actionPos = entries.last();
            } else {
                Position<Entry<K, V>> curr = entries.first();
                while (c.compare(e.getKey(), curr.element().getKey()) > 0) {
                    curr = entries.next(curr);
                }
                entries.addBefore(curr, e);
                actionPos = entries.prev(curr);
            }
        }

        public Entry<K, V> removeMin()
                throws EmptyPriorityQueueException, InvalidPositionException, EmptyListException {
            if (entries.isEmpty()) {
                throw new EmptyPriorityQueueException("Priority Queue is Empty.");
            } else {
                return entries.remove(entries.first());
            }
        }

        public void traverseSortedList(){
            for(Entry<K, V> e: entries){
                System.out.println(e.getValue() + " " + e.getKey());
            }
        }
    }

    public static void main(String args[])
            throws EmptyPriorityQueueException, InvalidKeyException, EmptyListException, InvalidPositionException, BoundaryViolationException {
        SortedListPriorityQueue<Integer, String> SPQ = new SortedListPriorityQueue<>();

        SPQ.insert(2, "Prerak");
        SPQ.insert(9, "John");
        SPQ.insert(1, "Mausam");
        SPQ.traverseSortedList();
        SPQ.removeMin();
        System.out.println();
        SPQ.traverseSortedList();

    }
}