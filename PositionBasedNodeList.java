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

    // position Interface

    public interface Position<E> {
        E element() throws InvalidPositionException;
    }

    // PositionList Interface

    public interface PositionList<E> extends Iterable<Position<E>> {

        public Iterator<Position<E>> iterator();

        public int size();

        public boolean isEmpty();

        public Position<E> first() throws EmptyListException;

        public Position<E> last() throws EmptyListException;

        public Position<E> next(Position<E> P) throws InvalidPositionException, BoundaryViolationException;

        public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException;

        public void addFirst(E e);

        public void addLast(E e);

        public void addAfter(Position<E> p, E e) throws InvalidPositionException;

        public void addBefore(Position<E> p, E e) throws InvalidPositionException;

        public E remove(Position<E> p) throws InvalidPositionException;

        public E set(Position<E> p, E e) throws InvalidPositionException;

        // public Iterable<Position<E>> positions() throws Exception;

    }

    // DNode Class

    public static class DNode<E> implements Position<E> {
        private DNode<E> prev, next;
        private E element;

        public DNode(DNode<E> newPrev, DNode<E> newNext, E elem) {
            this.element = elem;
            this.prev = newPrev;
            this.next = newNext;
        }

        public E element() throws InvalidPositionException {
            // if ((prev == null) && (next == null)) {
            //     throw new InvalidPositionException("Position is not in a list..!");
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
            header = new DNode<E>(null, null, null);
            trailer = new DNode<E>(header, null, null);
            header.setNext(trailer);
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
            DNode<E> newNode = new DNode<E>(v, v.getNext(), element);
            v.getNext().setPrev(newNode);
            v.setNext(newNode);
        }

        public void addBefore(Position<E> p, E element) throws InvalidPositionException {
            DNode<E> v = checkPosition(p);

            numElts++;
            DNode<E> newNode = new DNode<E>(v.getPrev(), v, element);
            v.getPrev().setNext(newNode);
            v.setPrev(newNode);
        }

        public void addFirst(E element) {
            numElts++;
            DNode<E> newNode = new DNode<E>(header, header.getNext(), element);
            header.getNext().setPrev(newNode);
            header.setNext(newNode);
        }

        public void addLast(E element) {
            numElts++;
            DNode<E> newNode = new DNode<E>(trailer.getPrev(), trailer, element);
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

        class ElementIterator implements Iterator<Position<E>> {
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

            public Position<E> next() {
                if (cursor == null)
                    throw new NoSuchElementException("No Next Element...!");

                Position<E> toReturn;

                try {
                    toReturn = cursor;

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

        public Iterator<Position<E>> iterator() {
            return new ElementIterator(this);
        }

        public String toString() {
            Iterator<Position<E>> it = this.iterator();
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

        public Iterable<Position<E>> positions(){
            PositionList<Position<E>> P = new NodePositionList<>();
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

    // public static class FavouriteList {

    //     protected PositionList fList;

    //     public FavouriteList() {
    //         fList = new NodePositionList<>();
    //     }

    //     public int size() {
    //         return fList.size();
    //     }

    //     public boolean isEmpty() {
    //         return fList.isEmpty();
    //     }

    //     public void remove(Position obj) {
    //         Position p = find(obj);
    //         if (p != null) {
    //             try {
    //                 fList.remove(p);
    //             } catch (Exception e) {
    //                 throw new NoSuchElementException("Can't find the object to remove");
    //             }

    //         }
    //     }

    //     protected int value(Position p) {
    //         return (p.element()).value();
    //     }

    //     protected Position find(Position obj) {
    //         try {
    //             for (Position p : fList.positions()) {
    //                 if (value(p).equals(obj)) {
    //                     return p;
    //                 }
    //             }
    //         } catch (Exception e) {
    //             throw new RuntimeException(e); // wrap it
    //         }
    //         return null;
    //     }

    //     public void access(int obj) {
    //         Position p = find(obj);
    //         if (p != null) {
    //             p.element().incrementCount();
    //         } else {
    //             fList.addLast(new Entry(obj));
    //             p.fList.last();
    //         }
    //         moveUp(p);
    //     }

    //     protected void moveUp(Position cur) {
    //         Entry e = cur.element();
    //         int c = count(cur);
    //         while (cur != fList.first()) {
    //             Position prev = fList.prev(cur);
    //             if (c <= count(prev)) {
    //                 break;
    //             }
    //             fList.set(cur, prev.element());
    //             cur = prev;
    //         }
    //     }

    //     public Iterable top(int k) {
    //         if (k < 0 || k > size()) {
    //             throw new IllegalArgumentException("Invalid argument");
    //         }
    //         PositionList T = new NodePositionList();

    //         int i = 0;
    //         for (Entry e : fList) {
    //             if (i++ >= k) {
    //                 break;
    //             }
    //             T.addLast(e.value());
    //         }
    //         return T;
    //     }

    //     public String toString() {
    //         return fList.toString();
    //     }

    //     protected int count(Position p) {
    //         return (p.element()).count();
    //     }

    //     protected static class Entry {
    //         private int value;
    //         private int count;

    //         Entry(int v) {
    //             count = 1;
    //             value = v;
    //         }

    //         public int value(){
    //             return value;
    //         }

    //         public int count() {
    //             return count;
    //         }

    //         public int incrementCount() {
    //             return ++count;
    //         }

    //         public String toString() {
    //             return "[" + count + ", " + value + "]";
    //         }

    //     }

    // }

    public static void main(String[] args) throws Exception {
        NodePositionList<Integer> list = new NodePositionList<>();
        list.addFirst(10);
        list.addLast(20);
        list.addLast(30);

        System.out.println(list.toString());

        Position<Integer> p = list.first();
        list.addAfter(p, 15);
        System.out.println(list.toString());
    }

}
