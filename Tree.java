import java.util.*;

import javax.swing.text.Position;

public class Tree {

    // Exceptions

    public class EmptyTreeException extends Exception {
        EmptyTreeException(Exception message) {
            super(message);
        }
    }

    public class InvalidPositionException extends Exception {
        InvalidPositionException(Exception message) {
            super(message);
        }
    }

    public class BoundaryViolationException extends Exception {
        BoundaryViolationException(Exception message) {
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

    // Interface Trees

    public interface Trees<E> {

        public int size();

        public boolean isEmpty();

        public Iterator<Position<E>> intrator();

        public E replace(Position<E> v, E e) throws InvalidPositionException;

        public Position<E> root() throws EmptyTreeException;

        public Position<E> parent() throws InvalidPositionException, BoundaryViolationException;

        public Iterable<BTPosition<E>> children(BTPosition<E> v) throws InvalidPositionException;

        public boolean isInternal(Position<E> v) throws InvalidPositionException;

        public boolean isExternal(Position<E> v) throws InvalidPositionException;

        public boolean isRoot(Position<E> v) throws InvalidPositionException;
    }

    // Interface BinaryTree

    public interface BinaryTree<E> extends Trees<E> {
        BTPosition<E> left(BTPosition<E> v) throws InvalidPositionException, BoundaryViolationException;

        BTPosition<E> right(BTPosition<E> v) throws InvalidPositionException, BoundaryViolationException;

        boolean hasLeft(BTPosition<E> v) throws InvalidPositionException;

        boolean hasRight(BTPosition<E> v) throws InvalidPositionException;;
    }

    // Class LinkedBinaryTree

    public class LinkedBinaryTree<E> implements BinaryTree<E> {
        protected BTPosition<E> root;
        protected int size;

        public LinkedBinaryTree() {
            root = null;
            size = 0;
        }

        // public boolean isInternal(Position<E> v){

        // }
    }

    // Class BTNode

    public class BTNode<E> implements BTPosition<E> {
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
}
