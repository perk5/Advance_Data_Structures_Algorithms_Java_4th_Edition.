import java.util.*;

public class Tree {

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

    public static class NonEmptyTreeException extends Exception {
        public NonEmptyTreeException(String message) {
            super(message);
        }
    }

    // Interface Position

    public interface Position<E> {
        E element() throws InvalidPositionException;
    }

    // Interface BTPosition

    public interface BTPosition<E> extends Position<E> {
        void setElement(E e);

        void setLeft(BTPosition<E> v);

        BTPosition<E> getLeft();

        void setRight(BTPosition<E> v);

        BTPosition<E> getRight();

        void setParent(BTPosition<E> v);

        BTPosition<E> getParent();
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
                    throw new InvalidPositionException("Position does not belong to this list");
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

    // Interface Trees

    public interface Trees<E> {

        public int size();

        public boolean isEmpty();

        public Iterator<E> iterator() throws InvalidPositionException, EmptyTreeException, BoundaryViolationException;

        public Iterable<Position<E>> positions()
                throws InvalidPositionException, EmptyTreeException, BoundaryViolationException;

        public E replace(Position<E> v, E e) throws InvalidPositionException;

        public Position<E> root() throws EmptyTreeException;

        public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException;

        public Iterable<Position<E>> children(Position<E> v)
                throws InvalidPositionException, BoundaryViolationException;

        public boolean isInternal(Position<E> v) throws InvalidPositionException;

        public boolean isExternal(Position<E> v) throws InvalidPositionException;

        public boolean isRoot(Position<E> v) throws InvalidPositionException, EmptyTreeException;
    }

    // Interface BinaryTree

    public interface BinaryTree<E> extends Trees<E> {
        public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException;

        public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException;

        public boolean hasLeft(Position<E> v) throws InvalidPositionException;

        public boolean hasRight(Position<E> v) throws InvalidPositionException;
    }

    // Class LinkedBinaryTree

    public static class LinkedBinaryTree<E> implements BinaryTree<E> {

        protected BTPosition<E> root;
        protected int size;

        public LinkedBinaryTree() {
            root = null;
            size = 0;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public Iterator<E> iterator() throws InvalidPositionException, EmptyTreeException, BoundaryViolationException {
            Iterable<Position<E>> positions = positions();
            PositionList<E> elements = new NodePositionList<E>();
            for (Position<E> pos : positions) {
                elements.addLast(pos.element());
            }
            return elements.iterator();
        }

        public E replace(Position<E> v, E o) throws InvalidPositionException {
            BTPosition<E> vv = checkPosition(v);
            E temp = v.element();
            vv.setElement(o);
            return temp;
        }

        public Position<E> root() throws EmptyTreeException {
            if (root == null) {
                throw new EmptyTreeException("The tree is Empty");
            }
            return root;
        }

        public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
            BTPosition<E> vv = checkPosition(v);
            Position<E> parentPos = vv.getParent();
            if (parentPos == null) {
                throw new BoundaryViolationException("No Parent.");
            }
            return parentPos;
        }

        public Iterable<Position<E>> children(Position<E> v)
                throws InvalidPositionException, BoundaryViolationException {
            PositionList<Position<E>> children = new NodePositionList<Position<E>>();

            if (hasLeft(v)) {
                children.addLast(left(v));
            }

            if (hasRight(v)) {
                children.addLast(right(v));
            }
            return children;
        }

        public boolean isInternal(Position<E> v) throws InvalidPositionException {
            checkPosition(v);
            return (hasLeft(v) || hasRight(v));
        }

        public boolean isExternal(Position<E> v) throws InvalidPositionException {
            checkPosition(v);
            return (!hasLeft(v) && !hasRight(v));

        }

        public boolean isRoot(Position<E> v) throws InvalidPositionException, EmptyTreeException {
            checkPosition(v);
            return v == root();
        }

        public boolean hasLeft(Position<E> v) throws InvalidPositionException {
            BTPosition<E> vv = checkPosition(v);
            return (vv.getLeft() != null);
        }

        public boolean hasRight(Position<E> v) throws InvalidPositionException {
            BTPosition<E> vv = checkPosition(v);
            return (vv.getRight() != null);
        }

        public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
            BTPosition<E> vv = checkPosition(v);

            Position<E> leftPos = vv.getLeft();
            if (leftPos == null) {
                throw new BoundaryViolationException("No Left Child");
            }
            return leftPos;
        }

        public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
            BTPosition<E> vv = checkPosition(v);

            Position<E> rightPos = vv.getRight();
            if (rightPos == null) {
                throw new BoundaryViolationException("No Right Child");
            }
            return rightPos;
        }

        public Iterable<Position<E>> positions()
                throws InvalidPositionException, EmptyTreeException, BoundaryViolationException {
            PositionList<Position<E>> position = new NodePositionList<Position<E>>();
            if (size != 0) {
                preorderPositions(root(), position);
            }
            return position;
        }

        public Position<E> sibling(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
            BTPosition<E> vv = checkPosition(v);
            BTPosition<E> parentPos = vv.getParent();
            if (parentPos != null) {
                BTPosition<E> sibPos;
                BTPosition<E> leftPos = parentPos.getLeft();
                if (leftPos == vv) {
                    sibPos = parentPos.getLeft();
                } else {
                    sibPos = parentPos.getLeft();
                }
                if (sibPos == null) {
                    return sibPos;
                }
            }
            throw new BoundaryViolationException("No Sibling");
        }

        public Position<E> addRoot(E e) throws NonEmptyTreeException {
            if (!isEmpty()) {
                throw new NonEmptyTreeException("Tree already has a root..");
            }
            size = 1;
            root = createNode(e, null, null, null);
            return root;
        }

        public Position<E> insertLeft(Position<E> v, E e) throws InvalidPositionException {
            BTPosition<E> vv = checkPosition(v);
            Position<E> leftPos = vv.getLeft();
            if (leftPos != null) {
                throw new InvalidPositionException("Node already has a left child");
            }
            BTPosition<E> ww = createNode(e, vv, null, null);
            vv.setLeft(ww);
            size++;
            return ww;
        }

         public Position<E> insertRight(Position<E> v, E e) throws InvalidPositionException {
            BTPosition<E> vv = checkPosition(v);
            Position<E> rightPos = vv.getRight();
            if (rightPos != null) {
                throw new InvalidPositionException("Node already has a left child");
            }
            BTPosition<E> ww = createNode(e, vv, null, null);
            vv.setRight(ww);
            size++;
            return ww;
        }

        public E remove(Position<E> v) throws InvalidPositionException {
            BTPosition<E> vv = checkPosition(v);
            BTPosition<E> leftPos = vv.getLeft();
            BTPosition<E> rightPos = vv.getRight();

            if (leftPos != null && rightPos != null) {
                throw new InvalidPositionException("Cannot remove node with two children");
            }
            BTPosition<E> ww;
            if (leftPos != null) {
                ww = leftPos;
            } else if (rightPos != null) {
                ww = rightPos;
            } else {
                ww = null;
            }

            if (vv == root) {
                if (ww != null) {
                    ww.setParent(null);
                }
                root = ww;
            } else {
                BTPosition<E> uu = vv.getParent();
                if (vv == uu.getLeft()) {
                    uu.setLeft(ww);
                } else {
                    uu.setRight(ww);
                }
                if (ww != null) {
                    ww.setParent(uu);
                }
            }
            size--;
            return v.element();

        }

        // Work in Progress traversal logic..

        // public void traverse() throws InvalidPositionException, EmptyTreeException, BoundaryViolationException{
        //     StringBuilder s = new StringBuilder();
        //     int count = 0;
        //     Position<E> prevPosition = null;
        //     for(Position<E> p : positions()){
        //         for(int i = count; i < 3; i++){
        //             s.append(" ");
        //         }
        //         s.append(p.element());
        //         for(int i = count; i < 2; i++){
        //             s.append(" ");
        //         }
        //         if(prevPosition != null){
        //             BTPosition<E> vv = checkPosition(prevPosition);
        //             s.append(vv.getRight().element());
        //         }
        //         System.out.println(s);
        //         prevPosition = p;
        //         count++;
        //         s.setLength(0);
        //     }
        // }

        public void attach(Position<E> v, BinaryTree<E> T1, BinaryTree<E> T2)
                throws InvalidPositionException, EmptyTreeException {
            BTPosition<E> vv = checkPosition(v);
            if (isInternal(v)) {
                throw new InvalidPositionException("Cannot attack from internal Node");
            }
            if (!T1.isEmpty()) {
                BTPosition<E> r1 = checkPosition(T1.root());
                vv.setLeft(r1);
                r1.setParent(vv);
            }

            if (!T2.isEmpty()) {
                BTPosition<E> r2 = checkPosition(T2.root());
                vv.setRight(r2);
                r2.setParent(vv);
            }
        }

        protected BTPosition<E> checkPosition(Position<E> v) throws InvalidPositionException {
            if (v == null || !(v instanceof BTPosition)) {
                throw new InvalidPositionException("The Position is valid");
            }
            return (BTPosition<E>) v;
        }

        protected BTPosition<E> createNode(E element, BTPosition<E> parent, BTPosition<E> left, BTPosition<E> right) {
            return new BTNode<E>(element, parent, left, right);
        }

        protected void preorderPositions(Position<E> v, PositionList<Position<E>> pos)
                throws InvalidPositionException, BoundaryViolationException {
            pos.addLast(v);
            if (hasLeft(v)) {
                preorderPositions(left(v), pos);
            }
            if (hasRight(v)) {
                preorderPositions(right(v), pos);
            }
        }

    }

    // Class BTNode

    public static class BTNode<E> implements BTPosition<E> {
        private E element;
        private BTPosition<E> parent, left, right;

        public BTNode(E element, BTPosition<E> parent, BTPosition<E> left, BTPosition<E> right) {
            setElement(element);
            setParent(parent);
            setLeft(left);
            setRight(right);
        }

        public E element() {
            return element;
        }

        public void setElement(E o) {
            element = o;
        }

        public void setParent(BTPosition<E> p) {
            parent = p;
        }

        public void setLeft(BTPosition<E> l) {
            left = l;
        }

        public void setRight(BTPosition<E> r) {
            right = r;
        }

        public BTPosition<E> getParent() {
            return parent;
        }

        public BTPosition<E> getLeft() {
            return left;
        }

        public BTPosition<E> getRight() {
            return right;
        }
    }

    public static void main(String[] args) throws NonEmptyTreeException, InvalidPositionException, EmptyTreeException, BoundaryViolationException {
        LinkedBinaryTree<Integer> LB = new LinkedBinaryTree<>();

        Position<Integer> p = LB.addRoot(1);
        LB.insertLeft(p, 2);
        LB.insertRight(p, 3);
        // LB.insertLeft(firstLeft, 4);
        // LB.insertRight(firstLeft, 5);

        // System.out.println();
        // LB.traverse();
        boolean ans = LB.hasRight(p);
        System.out.println(ans);
    }
}
