import java.util.*;

public class PriorityQueue {

    // Exceptions

    public static class EmptyPriorityQueueException extends Exception {
        public EmptyPriorityQueueException(String message) {
            super(message);
        }
    }

    public static class InvalidEntryException extends Exception {
        public InvalidEntryException(String message) {
            super(message);
        }
    }

    public static class InvalidKeyException extends Exception {
        public InvalidKeyException(String message) {
            super(message);
        }
    }

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

    // Interface Position

    public interface Position<K> {
        K element();
    }

    // Interface PositionList

    public interface PositionList<K> extends Iterable<K> {
        public Iterator<K> iterator();

        public int size();

        public boolean isEmpty();

        public Position<K> first() throws EmptyListException;

        public Position<K> last() throws EmptyListException;

        public Position<K> next(Position<K> P) throws InvalidPositionException, BoundaryViolationException;

        public Position<K> prev(Position<K> p) throws InvalidPositionException, BoundaryViolationException;

        public void addFirst(K e);

        public void addLast(K e);

        public void addAfter(Position<K> p, K e) throws InvalidPositionException;

        public void addBefore(Position<K> p, K e)
                throws InvalidPositionException, EmptyListException, BoundaryViolationException;

        public K remove(Position<K> p) throws InvalidPositionException;

        public K set(Position<K> p, K e) throws InvalidPositionException;

        public Iterable<Position<K>> positions()
                throws EmptyListException, InvalidPositionException, BoundaryViolationException;
    }

    // Class DNode

    public static class DNode<K> implements Position<K> {
        DNode<K> next, prev;
        K element;

        public DNode(K element, DNode<K> next, DNode<K> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }

        public K element() {
            return element;
        }

        public DNode<K> getPrev() {
            return prev;
        }

        public DNode<K> getNext() {
            return next;
        }

        public void setNext(DNode<K> newNode) {
            this.next = newNode;
        }

        public void setPrev(DNode<K> newNode) {
            this.prev = newNode;
        }

        public void setElement(K e) {
            this.element = e;
        }

    }

    public static class NodePositionList<K> implements PositionList<K> {
        DNode<K> header, trailer;
        int size;

        public NodePositionList() {
            this.header = new DNode<K>(null, null, null);
            this.trailer = new DNode<K>(null, null, header);
            header.next = trailer;
        }

        protected DNode<K> checkPosition(Position<K> p) throws InvalidPositionException {
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
                DNode<K> temp = (DNode<K>) p;
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

        public Position<K> first() throws EmptyListException {
            if (isEmpty()) {
                throw new EmptyListException("List is Empty.");
            }
            return header.getNext();
        }

        public Position<K> last() throws EmptyListException {
            if (isEmpty()) {
                throw new EmptyListException("List is Empty.");
            }
            return trailer.getPrev();
        }

        public Position<K> next(Position<K> p) throws InvalidPositionException, BoundaryViolationException {
            DNode<K> currentPosition = checkPosition(p);

            return currentPosition.getNext();
        }

        public Position<K> prev(Position<K> p) throws InvalidPositionException, BoundaryViolationException {
            DNode<K> currentPosition = checkPosition(p);

            return currentPosition.getPrev();
        }

        public void addFirst(K e) {

            DNode<K> first = header.getNext();
            DNode<K> newNode = new DNode<K>(e, first, header);

            header.setNext(newNode);
            first.setPrev(newNode);

            size++;
        }

        public void addLast(K e) {
            DNode<K> last = trailer.getPrev();
            DNode<K> newNode = new DNode<K>(e, last, trailer);

            last.setNext(newNode);
            trailer.setPrev(newNode);

            size++;
        }

        public void addAfter(Position<K> p, K e) throws InvalidPositionException {
            DNode<K> node = checkPosition(p);
            DNode<K> next = node.getNext();
            DNode<K> newNode = new DNode<K>(e, node.getNext(), node);

            node.setNext(newNode);
            next.setPrev(newNode);

            size++;

        }

        public void addBefore(Position<K> p, K e)
                throws InvalidPositionException, EmptyListException, BoundaryViolationException {
            p = checkPosition(p);

            if (p == first())
                addFirst(e);
            else
                addAfter(prev(p), e);
        }

        public K remove(Position<K> p) throws InvalidPositionException {
            DNode<K> v = checkPosition(p);
            size--;
            DNode<K> vPrev = v.getPrev();
            DNode<K> vNext = v.getNext();
            vPrev.setNext(vNext);
            vNext.setPrev(vPrev);
            K vElem = v.element();
            v.setNext(null);
            v.setPrev(null);
            return vElem;
        }

        public K set(Position<K> p, K e) throws InvalidPositionException {
            DNode<K> v = checkPosition(p);
            K oldElt = v.element();
            v.setElement(e);
            return oldElt;
        }

        public Iterable<Position<K>> positions()
                throws EmptyListException, InvalidPositionException, BoundaryViolationException {
            PositionList<Position<K>> P = new NodePositionList<Position<K>>();
            if (!isEmpty()) {
                Position<K> p = first();
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

        public class ElementIterator implements Iterator<K> {
            protected PositionList<K> list;
            protected Position<K> cursor;

            public ElementIterator(PositionList<K> L) {
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

            public K next() {
                if (cursor == null)
                    throw new NoSuchElementException("No Next Element...!");

                K toReturn;

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

        public Iterator<K> iterator() {
            return new ElementIterator(this);
        }
    }

    public static interface Entry<K, V> {
        K getKey();

        V getValue();
    }

    public interface IPriorityQueue<K, V> {
        public int size();

        public boolean isEmpty();

        public Entry<K, V> min() throws EmptyPriorityQueueException, EmptyListException, EmptyTreeException;

        public Entry<K, V> insert(K key, V value)
                throws InvalidKeyException, EmptyTreeException, EmptyListException, InvalidPositionException,
                BoundaryViolationException;

        public Entry<K, V> removeMin()
                throws BoundaryViolationException, EmptyPriorityQueueException, EmptyTreeException,
                InvalidPositionException, EmptyListException;

    }

    public interface AdaptablePriorityQueue<K, V> {
        Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException, InvalidPositionException;

        K replaceKey(Entry<K, V> e, K key) throws InvalidEntryException, InvalidKeyException, EmptyListException,
                InvalidPositionException, BoundaryViolationException;

        V replaceValue(Entry<K, V> e, V value) throws InvalidEntryException;
    }

    // SortedList Adaptable Priority Queue.

    public class SortedListAdaptablePriorityQueue<K, V> extends SortedListPriorityQueue<K, V>
            implements AdaptablePriorityQueue<K, V> {

        public SortedListAdaptablePriorityQueue() {
            super();
        }

        public SortedListAdaptablePriorityQueue(Comparator<K> comp) {
            super(comp);
        }

        public Entry<K, V> insert(K k, V v)
                throws InvalidKeyException, EmptyListException, InvalidPositionException, BoundaryViolationException {
            checkKey(k);
            LocationAwareEntry<K, V> entry = new LocationAwareEntry<>(k, v);
            insertEntry(entry);
            entry.setLocation(actionPos);
            return entry;
        }

        public Entry<K, V> remove(Entry<K, V> entry) throws InvalidEntryException, InvalidPositionException {
            checkEntry(entry);
            LocationAwareEntry<K, V> e = (LocationAwareEntry<K, V>) entry;
            Position<Entry<K, V>> p = e.location();
            entries.remove(p);
            e.setLocation(null);
            return e;
        }

        public K replaceKey(Entry<K, V> entry, K k) throws InvalidEntryException, InvalidKeyException,
                EmptyListException, InvalidPositionException, BoundaryViolationException {
            checkKey(k);
            checkEntry(entry);
            LocationAwareEntry<K, V> e = (LocationAwareEntry<K, V>) remove(entry);
            K oldKey = e.setKey(k);
            insertEntry(e);
            e.setLocation(actionPos);
            return oldKey;
        }

        public V replaceValue(Entry<K, V> e, V value) throws InvalidEntryException {
            checkEntry(e);
            V oldValue = ((LocationAwareEntry<K, V>) e).setValue(value);
            return oldValue;
        }

        protected void checkEntry(Entry<K, V> ent) throws InvalidEntryException {
            if (ent == null || !(ent instanceof LocationAwareEntry)) {
                throw new InvalidEntryException("invalid entry");
            }
        }

        protected static class LocationAwareEntry<K, V> extends MyEntry<K, V> {
            private Position<Entry<K, V>> loc;

            public LocationAwareEntry(K key, V value) {
                super(key, value);
            }

            public LocationAwareEntry(K key, V value, Position<Entry<K, V>> pos) {
                super(key, value);
                loc = pos;
            }

            protected Position<Entry<K, V>> location() {
                return loc;
            }

            protected Position<Entry<K, V>> setLocation(Position<Entry<K, V>> pos) {
                Position<Entry<K, V>> oldPosition = location();
                loc = pos;
                return oldPosition;
            }

            protected K setKey(K key) {
                K oldKey = getKey();
                this.key = key;
                return oldKey;
            }

            protected V setValue(V value) {
                V oldValue = getValue();
                this.value = value;
                return oldValue;
            }

        }

    }

    
    // SortingList PriorityQueue......

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

        public class DefaultComparator<K> implements Comparator<K> {
            public int compare(K a, K b) throws ClassCastException {
                return ((Comparable<K>) a).compareTo(b);
            }
        }

        

        protected void checkKey(K k) throws InvalidKeyException {
            try {
                c.compare(k, k);
            } catch (ClassCastException | NullPointerException e) {
                throw new InvalidKeyException("Key cannot be compared");
            }
        }

        protected static class MyEntry<K, V> implements Entry<K, V> {

            protected K key;
            protected V value;

            MyEntry(K key, V value) {
                this.key = key;
                this.value = value;
            }

            public K getKey() {
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

        public void traverseSortedList() {
            for (Entry<K, V> e : entries) {
                System.out.println(e.getValue() + " " + e.getKey());
            }
        }
    }

    // Java Implementation of complete BinaryTree (Heaps --> ArrayList)

    // Interface Trees

    public interface Trees<K> {

        public int size();

        public boolean isEmpty();

        public Iterator<K> iterator()
                throws InvalidPositionException, EmptyTreeException, BoundaryViolationException;

        // public Iterable<Position<K >> positions()
        // throws InvalidPositionException, EmptyTreeException,
        // BoundaryViolationException;

        public K replace(Position<K> v, K e) throws InvalidPositionException;

        public Position<K> root() throws EmptyTreeException;

        public Position<K> parent(Position<K> v) throws InvalidPositionException, BoundaryViolationException;

        // public Iterable<Position<K >> children(Position<K > v)
        // throws InvalidPositionException, BoundaryViolationException;

        public boolean isInternal(Position<K> v) throws InvalidPositionException;

        public boolean isExternal(Position<K> v) throws InvalidPositionException;

        public boolean isRoot(Position<K> v) throws InvalidPositionException, EmptyTreeException;
    }

    // Interface BinaryTree

    public interface BinaryTree<K> extends Trees<K> {
        public Position<K> left(Position<K> v) throws InvalidPositionException, BoundaryViolationException;

        public Position<K> right(Position<K> v) throws InvalidPositionException, BoundaryViolationException;

        public boolean hasLeft(Position<K> v) throws InvalidPositionException;

        public boolean hasRight(Position<K> v) throws InvalidPositionException;
    }

    // CompleteBinaryTree Interface

    public interface CompleteBinaryTree<K> extends BinaryTree<K> {
        public Position<K> add(K elem);

        public K remove() throws EmptyTreeException;
    }

    // ArrayListCompleteBinaryTree class

    public static class ArrayListCompleteBinaryTree<K> implements CompleteBinaryTree<K> {
        protected ArrayList<BTPos<K>> T;

        protected static class BTPos<K> implements Position<K> {
            K element;
            int index;

            public BTPos(K element, int index) {
                this.element = element;
                this.index = index;
            }

            public K element() {
                return element;
            }

            public int index() {
                return index;
            }

            public K setElement(K elt) {
                K temp = element;
                element = elt;
                return temp;
            }
        }

        public ArrayListCompleteBinaryTree() {
            T = new ArrayList<BTPos<K>>();

            T.add(0, null);
        }

        public int size() {
            return T.size() - 1;
        }

        public boolean isEmpty() {
            return (size() == 0);
        }

        public boolean isInternal(Position<K> v) throws InvalidPositionException {
            return hasLeft(v);
        }

        public boolean isExternal(Position<K> v) throws InvalidPositionException {
            return !isInternal(v);
        }

        protected BTPos<K> checkPosition(Position<K> v) throws InvalidPositionException {
            if (v == null || !(v instanceof BTPos)) {
                throw new InvalidPositionException("Position is invalid");
            }
            return (BTPos<K>) v;
        }

        public boolean isRoot(Position<K> v) throws InvalidPositionException {
            BTPos<K> vv = checkPosition(v);

            return vv.index == 1;
        }

        public Position<K> root() throws EmptyTreeException {
            if (isEmpty()) {
                throw new EmptyTreeException("The Tree is Empty..");
            }
            return T.get(1);
        }

        public Position<K> left(Position<K> v) throws InvalidPositionException, BoundaryViolationException {
            if (!hasLeft(v)) {
                throw new BoundaryViolationException("No Left Child");
            }
            BTPos<K> vv = checkPosition(v);
            return T.get(2 * vv.index());
        }

        public Position<K> right(Position<K> v) throws InvalidPositionException, BoundaryViolationException {
            if (!hasRight(v)) {
                throw new BoundaryViolationException("No Right Child");
            }
            BTPos<K> vv = checkPosition(v);
            return T.get(2 * vv.index() + 1);
        }

        public boolean hasLeft(Position<K> v) throws InvalidPositionException {
            BTPos<K> vv = checkPosition(v);
            return 2 * vv.index() <= size();
        }

        public boolean hasRight(Position<K> v) throws InvalidPositionException {
            BTPos<K> vv = checkPosition(v);
            return 2 * vv.index() + 1 <= size();
        }

        public K replace(Position<K> v, K e) throws InvalidPositionException {
            BTPos<K> vv = checkPosition(v);
            return vv.setElement(e);
        }

        public Position<K> parent(Position<K> v) throws InvalidPositionException, BoundaryViolationException {
            if (isRoot(v)) {
                throw new BoundaryViolationException("No Parent");
            }
            BTPos<K> vv = checkPosition(v);
            return T.get(vv.index / 2);
        }

        public Position<K> add(K elem) {
            int i = T.size();
            BTPos<K> p = new BTPos<K>(elem, i);
            T.add(p);
            return p;
        }

        public K remove() throws EmptyTreeException {
            if (isEmpty()) {
                throw new EmptyTreeException("The Tree is Empty..");
            }
            return T.remove(size()).element();
        }

        public Iterator<K> iterator()
                throws InvalidPositionException, EmptyTreeException, BoundaryViolationException {
            ArrayList<K> list = new ArrayList<>();

            for (int i = 1; i < T.size(); i++) {
                if (T.get(i) != null)
                    list.add(T.get(i).element());
            }
            return list.iterator();
        }

        public void traverse() {
            for (int i = 1; i < T.size(); i++) {
                if (T.get(i) == null) {
                    continue;
                }
                System.out.println(T.get(i).index() + " " + T.get(i).element());
            }
        }

    }

    // Java Heap Implementation BubbleUp and BubbleDown Technique (MaxHeap)

    public static class MaxHeapPriorityQueue<K, V> {
        protected HeapPriorityQueue<K, V> heap;

        public class faultComparator<K> implements Comparator<K> {
            public int compare(K a, K b) throws ClassCastException {
                return ((Comparable<K>) b).compareTo(a);
            }
        }

        public MaxHeapPriorityQueue() {
            Comparator<K> reverse = new faultComparator<>();
            heap = new HeapPriorityQueue<>(reverse);

        }

        public Entry<K, V> removeMax() throws EmptyPriorityQueueException, EmptyTreeException, InvalidPositionException,
                BoundaryViolationException {
            return heap.removeMin();
        }

        public Entry<K, V> insert(K k, V v) throws Exception {
            return heap.insert(k, v);
        }

        public Entry<K, V> max() throws Exception {
            return heap.min();
        }

    }

    // Java Heap Implementation BubbleUp and BubbleDown Technique

    public static class HeapPriorityQueue<K, V> implements IPriorityQueue<K, V> {
        protected CompleteBinaryTree<Entry<K, V>> heap;
        protected Comparator<K> comp;

        protected static class MyEntry<K, V> implements Entry<K, V> {

            private K key;
            private V value;

            MyEntry(K key, V value) {
                this.key = key;
                this.value = value;
            }

            public K getKey() {
                return key;
            }

            public V getValue() {
                return value;
            }

            public String toString() {
                return "(" + key + "," + value + ")";
            }
        }

        public HeapPriorityQueue() {
            heap = new ArrayListCompleteBinaryTree<>();
            comp = new DefaultComparator<K>();
        }

        public class DefaultComparator<K> implements Comparator<K> {
            public int compare(K a, K b) throws ClassCastException {
                return ((Comparable<K>) a).compareTo(b);
            }
        }

        public HeapPriorityQueue(Comparator<K> c) {
            heap = new ArrayListCompleteBinaryTree<>();
            comp = c;
        }

        public int size() {
            return heap.size();
        }

        public boolean isEmpty() {
            return heap.size() == 0;
        }

        public Entry<K, V> min() throws EmptyPriorityQueueException, EmptyTreeException {
            if (isEmpty()) {
                throw new EmptyPriorityQueueException("Priority Queue is Empty");
            } else {
                return heap.root().element();
            }
        }

        public void checkKey(K key) throws InvalidKeyException {
            try {
                comp.compare(key, key);
            } catch (ClassCastException | NullPointerException e) {
                throw new InvalidKeyException("Key cannot be compared");
            }
        }

        protected void upHeap(Position<Entry<K, V>> v)
                throws EmptyTreeException, InvalidPositionException, BoundaryViolationException {
            Position<Entry<K, V>> u;
            while (!heap.isRoot(v)) {
                u = heap.parent(v);
                if (comp.compare(u.element().getKey(), v.element().getKey()) <= 0) {
                    break;
                }
                swap(u, v);
                v = u;
            }
        }

        public Entry<K, V> insert(K key, V value) throws InvalidKeyException, EmptyListException,
                InvalidPositionException, BoundaryViolationException, EmptyTreeException {
            checkKey(key);
            Entry<K, V> entry = new MyEntry<K, V>(key, value);
            upHeap(heap.add(entry));
            return entry;
        }

        public void downHeap(Position<Entry<K, V>> r) throws InvalidPositionException, BoundaryViolationException {
            while (heap.isInternal(r)) {
                Position<Entry<K, V>> s;
                if (!heap.hasRight(r)) {
                    s = heap.left(r);
                } else if (comp.compare(heap.left(r).element().getKey(), heap.right(r).element().getKey()) <= 0) {
                    s = heap.left(r);
                } else {
                    s = heap.right(r);
                }

                if (comp.compare(s.element().getKey(), r.element().getKey()) < 0) {
                    swap(r, s);
                    r = s;
                } else {
                    break;
                }
            }
        }

        protected void swap(Position<Entry<K, V>> x, Position<Entry<K, V>> y) throws InvalidPositionException {
            Entry<K, V> temp = x.element();
            heap.replace(x, y.element());
            heap.replace(y, temp);

        }

        public Entry<K, V> removeMin()
                throws EmptyPriorityQueueException, EmptyTreeException, InvalidPositionException,
                BoundaryViolationException {
            if (isEmpty()) {
                throw new EmptyPriorityQueueException("Priority queue is Empty");
            }
            Entry<K, V> min = heap.root().element();
            if (size() == 1) {
                heap.remove();
            } else {
                heap.replace(heap.root(), heap.remove());
                downHeap(heap.root());
            }
            return min;
        }

        public String toString() {
            return heap.toString();
        }

        public void traverse() throws InvalidPositionException, EmptyTreeException, BoundaryViolationException {
            Iterator<Entry<K, V>> i = heap.iterator();
            while (i.hasNext()) {
                Entry<K, V> e = i.next();
                System.out.println(e.getKey() + " " + e.getValue());
            }
        }
    }

    public static void main(String args[])
            throws EmptyPriorityQueueException, InvalidKeyException, EmptyTreeException, EmptyListException,
            InvalidPositionException,
            BoundaryViolationException, Exception {
        SortedListPriorityQueue<Integer, String> SPQ = new SortedListPriorityQueue<>();
        SPQ.insert(2, "John");
        SPQ.insert(9, "Rohan");
        SPQ.insert(1, "Aaron");
        SPQ.traverseSortedList();
        // SPQ.removeMin();
        // System.out.println();
        // SPQ.traverseSortedList();

        // Heaps Using ArrayList....
        // ArrayListCompleteBinaryTree<String> BT = new ArrayListCompleteBinaryTree<>();
        // BT.add("John");
        // BT.add("Rohan");
        // BT.add("Aaron");
        // BT.traverse();

        // Heaps Using BubbleUp and BubbleDown approach...
        HeapPriorityQueue<Integer, String> HPQ = new HeapPriorityQueue<>();
    //     HPQ.insert(20, "jack");
    //     HPQ.insert(30, "Miter");
    //     HPQ.insert(10, "Peter");
    //     HPQ.insert(5, "Raj");
    //     // System.out.println(HPQ.removeMax());
    //    HPQ.traverse();

    }
}