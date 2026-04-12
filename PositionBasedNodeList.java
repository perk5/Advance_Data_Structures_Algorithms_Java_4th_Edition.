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

    public interface Position {
        int element() throws InvalidPositionException;
    }

    // PositionList Interface

    public interface PositionList {

        public int size();

        public boolean isEmpty();

        public Position first() throws EmptyListException;

        public Position last() throws EmptyListException;

        public Position next(Position P) throws InvalidPositionException, BoundaryViolationException;

        public Position prev(Position p) throws InvalidPositionException, BoundaryViolationException;

        public void addFirst(int e);

        public void addLast(int e);

        public void addAfter(Position p, int e) throws InvalidPositionException;

        public void addBefore(Position p, int e) throws InvalidPositionException;

        public int remove(Position p) throws InvalidPositionException;

        public int set(Position p, int e) throws InvalidPositionException;

    }

    // DNode Class

    public static class DNode implements Position {
        private DNode prev, next;
        private Integer element;

        public DNode(DNode newPrev, DNode newNext, Integer elem) {
            this.element = elem;
            this.prev = newPrev;
            this.next = newNext;
        }

        public int element() throws InvalidPositionException {
            // if ((prev == null) && (next == null)) {
            // throw new InvalidPositionException("Position is not in a list..!");
            // }
            return element;
        }

        public DNode getNext() {
            return next;
        }

        public DNode getPrev() {
            return prev;
        }

        public void setNext(DNode newNext) {
            this.next = newNext;
        }

        public void setPrev(DNode newPrev) {
            this.prev = newPrev;
        }

        public void setElement(int newElement) {
            this.element = newElement;
        }
    }

    // NodePositionList Class

    public static class NodePositionList implements PositionList {
        protected int numElts;
        protected DNode header, trailer;

        public NodePositionList() {
            numElts = 0;
            header = new DNode(null, null, null);
            trailer = new DNode(header, null, null);
            header.setNext(trailer);
        }

        protected DNode checkPosition(Position p) throws InvalidPositionException {
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
                DNode temp = (DNode) p;
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

        public Position first() throws EmptyListException {
            if (isEmpty()) {
                throw new EmptyListException("List is Empty.");
            }
            System.out.println(numElts);
            return header.getNext();
        }

        public Position last() throws EmptyListException {
            if (isEmpty()) {
                throw new EmptyListException("List is Empty.");
            }
            return trailer.getPrev();
        }

        public Position prev(Position p) throws InvalidPositionException, BoundaryViolationException {
            DNode v = checkPosition(p);
            DNode prev = v.getPrev();

            if (prev == header) {
                throw new BoundaryViolationException("Cannot advance past the beginning of the list");
            }
            return prev;
        }

        public Position next(Position p) throws InvalidPositionException, BoundaryViolationException {
            DNode v = checkPosition(p);
            DNode next = v.getNext();

            if (next == trailer) {
                throw new BoundaryViolationException("Cannot advance past the end of the list");
            }
            return next;
        }

        public void addAfter(Position p, int element) throws InvalidPositionException {
            DNode v = checkPosition(p);

            numElts++;
            DNode newNode = new DNode(v, v.getNext(), element);
            v.getNext().setPrev(newNode);
            v.setNext(newNode);
        }

        public void addBefore(Position p, int element) throws InvalidPositionException {
            DNode v = checkPosition(p);

            numElts++;
            DNode newNode = new DNode(v.getPrev(), v, element);
            v.getPrev().setNext(newNode);
            v.setPrev(newNode);
        }

        public void addFirst(int element) {
            numElts++;
            DNode newNode = new DNode(header, header.getNext(), element);
            header.getNext().setPrev(newNode);
            header.setNext(newNode);
        }

        public void addLast(int element) {
            numElts++;
            DNode newNode = new DNode(trailer.getPrev(), trailer, element);
            trailer.getPrev().setNext(newNode);
            trailer.setPrev(newNode);
        }

        public int remove(Position p) throws InvalidPositionException {
            DNode v = checkPosition(p);
            numElts--;
            DNode vPrev = v.getPrev();
            DNode vNext = v.getNext();
            vPrev.setNext(vNext);
            vNext.setPrev(vPrev);
            int vElem = v.element();
            v.setNext(null);
            v.setPrev(null);
            return vElem;
        }

        public int set(Position p, int element) throws InvalidPositionException {
            DNode v = checkPosition(p);
            int oldElt = v.element();
            v.setElement(element);
            return oldElt;
        }
    }

    public static void main(String[] args) throws Exception{
        NodePositionList list = new NodePositionList();
        list.addFirst(10);
        list.addLast(20);
        list.addLast(30);

        Position p = list.first();
        list.addAfter(p, 15);
    }

}
