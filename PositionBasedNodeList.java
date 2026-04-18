import java.util.*;

public class PositionBasedNodeList {

    // Exceptions

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

    // Position Interface

    public interface Position<E> {
        E element() throws InvalidPositionException;
    }

    // PositionList Interface

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

        public void reverse();

    }

    // DNode Class

    public static class DNode<E> implements Position<E> {
        private DNode<E> prev, next;
        private E element;
        private NodePositionList<E> list;

        public DNode(DNode<E> newPrev, DNode<E> newNext, E elem, NodePositionList<E> list) {
            this.element = elem;
            this.prev = newPrev;
            this.next = newNext;
            this.list = list;
        }

        public E element() throws InvalidPositionException {
            // if ((prev == null) && (next == null)) {
            // throw new InvalidPositionException("Position is not in a list..!");
            // }
            return element;
        }

        public DNode<E> getNext() {
            return next;
        }

        public DNode<E> getPrev() {
            return prev;
        }

        public void setNext(DNode<E> newNext) {
            this.next = newNext;
        }

        public void setPrev(DNode<E> newPrev) {
            this.prev = newPrev;
        }

        public void setElement(E newElement) {
            this.element = newElement;
        }
    }

    // NodePositionList Class

    public static class NodePositionList<E> implements PositionList<E> {
        public int numElts;
        protected DNode<E> header, trailer;

        public NodePositionList() {
            numElts = 0;
            header = new DNode<E>(null, null, null, this);
            trailer = new DNode<E>(header, null, null, this);
            header.setNext(trailer);
        }

        public void reverse() {

            DNode<E> current = header;

            while (current != null) {

                // swap next and prev pointers
                DNode<E> temp = current.getNext();
                current.setNext(current.getPrev());
                current.setPrev(temp);

                // move to next node in original order
                current = temp;
            }

            // swap header and trailer references
            DNode<E> temp = header;
            header = trailer;
            trailer = temp;
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
                if (temp.list != this)
                    throw new InvalidPositionException(
                            "Position does not belong to this list");
                return temp;
            } catch (ClassCastException e) {
                throw new InvalidPositionException("Position is of wrong type for this list");
            }

        }

        public int size() {
            return numElts;
        }

        public boolean isEmpty() {
            return numElts == 0;
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

        public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
            DNode<E> v = checkPosition(p);
            DNode<E> prev = v.getPrev();

            if (prev == header) {
                throw new BoundaryViolationException("Cannot advance past the beginning of the list");
            }
            return prev;
        }

        public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
            DNode<E> v = checkPosition(p);
            DNode<E> next = v.getNext();

            if (next == trailer) {
                throw new BoundaryViolationException("Cannot advance past the end of the list");
            }
            return next;
        }

        public void addAfter(Position<E> p, E element) throws InvalidPositionException {
            DNode<E> v = checkPosition(p);

            numElts++;
            DNode<E> newNode = new DNode<E>(v, v.getNext(), element, this);
            v.getNext().setPrev(newNode);
            v.setNext(newNode);
        }

        public void addBefore(Position<E> p, E element)
                throws InvalidPositionException, EmptyListException, BoundaryViolationException {
            p = checkPosition(p);

            p = checkPosition(p);

            if (p == first())
                addFirst(element);
            else
                addAfter(prev(p), element);

            // numElts++;
            // DNode<E> newNode = new DNode<E>(v.getPrev(), v, element);
            // v.getPrev().setNext(newNode);
            // v.setPrev(newNode);
        }

        public void addFirst(E element) {
            numElts++;
            DNode<E> newNode = new DNode<E>(header, header.getNext(), element, this);
            header.getNext().setPrev(newNode);
            header.setNext(newNode);
        }

        public void addLast(E element) {
            numElts++;
            DNode<E> newNode = new DNode<E>(trailer.getPrev(), trailer, element, this);
            trailer.getPrev().setNext(newNode);
            trailer.setPrev(newNode);
        }

        public E remove(Position<E> p) throws InvalidPositionException {
            DNode<E> v = checkPosition(p);
            numElts--;
            DNode<E> vPrev = v.getPrev();
            DNode<E> vNext = v.getNext();
            vPrev.setNext(vNext);
            vNext.setPrev(vPrev);
            E vElem = v.element();
            v.setNext(null);
            v.setPrev(null);
            return vElem;
        }

        public E set(Position<E> p, E element) throws InvalidPositionException {
            DNode<E> v = checkPosition(p);
            E oldElt = v.element();
            v.setElement(element);
            return oldElt;
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

        public String toString(PositionList<E> l) {
            Iterator<E> it = l.iterator();
            String s = "[";
            while (it.hasNext()) {
                s += it.next();
                if (it.hasNext()) {
                    s += ", ";
                }
            }
            s += "]";

            return s;
        }

        @Override
        public String toString() {
            Iterator<E> it = iterator();
            String s = "[";

            while (it.hasNext()) {
                s += it.next();
                if (it.hasNext())
                    s += ", ";
            }

            s += "]";
            return s;
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
    }

    public static class FavoriteList<E> {

        protected PositionList<Entry<E>> fList;

        public FavoriteList() {
            fList = new NodePositionList<>();
        }

        public int size() {
            return fList.size();
        }

        public boolean isEmpty() {
            return fList.isEmpty();
        }

        public void remove(E obj) throws InvalidPositionException {
            Position<Entry<E>> p = find(obj);
            if (p != null) {

                fList.remove(p);

            }
        }

        protected E value(Position<Entry<E>> p) throws InvalidPositionException {
            return (p.element()).value();
        }

        protected Position<Entry<E>> find(E obj) {
            try {
                for (Position<Entry<E>> p : fList.positions()) {
                    if (value(p).equals(obj)) {
                        return p;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        public void access(E obj)
                throws EmptyListException,
                BoundaryViolationException,
                InvalidPositionException {
            Position<Entry<E>> p = find(obj);
            if (p != null) {
                p.element().incrementCount();
            } else {
                fList.addLast(new Entry<>(obj));
                p = fList.last();
            }
            moveUp(p);
        }

        protected void moveUp(Position<Entry<E>> cur) throws EmptyListException,
                BoundaryViolationException,
                InvalidPositionException {
            Entry<E> e = cur.element();
            int c = count(cur);
            while (cur != fList.first()) {
                Position<Entry<E>> prev = fList.prev(cur);
                if (c <= count(prev)) {
                    break;
                }
                fList.set(cur, prev.element());
                cur = prev;
            }
            fList.set(cur, e);
        }

        public Iterable<E> top(int k) throws EmptyListException, InvalidPositionException, BoundaryViolationException {
            if (k < 0 || k > size()) {
                throw new IllegalArgumentException("Invalid argument");
            }
            PositionList<E> T = new NodePositionList<E>();

            int i = 0;
            for (Entry<E> e : fList) {
                if (i++ >= k) {
                    break;
                }
                T.addLast(e.value());
            }
            return T;
        }

        public String toString() {
            return fList.toString();
        }

        protected int count(Position<Entry<E>> p) {
            try {
                return (p.element()).count();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        protected static class Entry<E> {
            private E value;
            private int count;

            Entry(E v) {
                count = 1;
                value = v;
            }

            public E value() {
                return value;
            }

            public int count() {
                return count;
            }

            public int incrementCount() {
                return ++count;
            }

            public String toString() {
                return "[" + count + ", " + value + "]";
            }

        }
    }

    public static class FavoriteListMTF<E> extends FavoriteList<E> {

        public FavoriteListMTF() {

        }

        @Override
        protected void moveUp(Position<Entry<E>> pos) throws InvalidPositionException {
            fList.addFirst(fList.remove(pos));
        }

        @Override
        public Iterable<E> top(int k) throws EmptyListException, InvalidPositionException, BoundaryViolationException {
            if (k < 0 || k > size()) {
                throw new IllegalArgumentException("Invalid argument");
            }
            PositionList<E> T = new NodePositionList<E>();

            if (!isEmpty()) {
                PositionList<Entry<E>> C = new NodePositionList<Entry<E>>();
                for (Entry<E> e : fList) {
                    C.addLast(e);
                }
                for (int i = 0; i < k; i++) {
                    Position<Entry<E>> maxPos = null;
                    int maxCount = -1;

                    for (Position<Entry<E>> p : C.positions()) {
                        int c = count(p);
                        if (c > maxCount) {
                            maxCount = c;
                            maxPos = p;
                        }
                    }

                    T.addLast(value(maxPos));
                    C.remove(maxPos);

                }

            }
            return T;
        }
    }

    public static void main(String[] args) throws EmptyListException,
            BoundaryViolationException,
            InvalidPositionException {
        NodePositionList<Integer> list = new NodePositionList<>();
        NodePositionList<Integer> list1 = new NodePositionList<>();
        list.addFirst(10);
        list.addLast(20);
        list.addLast(30);

        System.out.println(list.toString(list));

        Position<Integer> p = list.first();
        // Position<Integer> p2 = list.last();
        // list.addAfter(p, 15);

        list.addAfter(p, 12);

        System.out.println(list.toString(list));
        // list.reverse();
        // System.out.println(list.toString(list));

        // FavoriteList<Integer> f = new FavoriteList<>();

        // f.access(10);
        // f.access(20);

        // f.access(20);f.access(20);
        // f.access(10);
        // f.access(20);
        // f.access(10);

        // System.out.println(f.toString());

    }

    }
//Bubble sorting Dummy Code....!!
// method swapping{
    // Node temp;
    // boolean swapped = true;
    // while(swapped){ 
    //     swapped = false; 
    //     temp= header.next;
    //     while(temp.next != trailer){ 
    //         if(temp.element > temp.next.element){ 
    //             swapped = true; 
    //             E t = temp.element; 
    //             temp.element = temp.next.element; 
    //             temp.next.element = t; 
    //         } 
    //             temp = temp.next; 
    //     } 
    //     }
// }