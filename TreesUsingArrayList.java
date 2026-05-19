import java.util.*;

public class TreesUsingArrayList {

    public static interface Position<E> {
        E element();
    }

    class ArrayPosition<E> implements Position<E> {

        private int index;
        private E element;

        ArrayPosition(int index, E element) {
            this.index = index;
            this.element = element;
        }

        public E element() {
            return element;
        }

        public int getIndex() {
            return index;
        }

    }

    public static interface Trees<E> {

        int size();

        boolean isEmpty();

        Position<E> root();

        Position<E> parent(Position<E> p);

        Iterable<Position<E>> children(Position<E> p);
    }

    public static interface BinaryTree<E> extends Trees<E> {

        Position<E> left(Position<E> p);

        Position<E> right(Position<E> p);

        boolean hasLeft(Position<E> p);

        boolean hasRight(Position<E> p);

    }

    public static class ArrayBinaryTree<E> implements BinaryTree<E> {

        private ArrayList<ArrayPosition<E>> tree;
        protected int size;

        public ArrayBinaryTree() {
            tree = new ArrayList<>();
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public Position<E> root() {
            return tree.get(0);
        }

        public Position<E> parent(Position<E> p) {
            ArrayPosition<E> n = (ArrayPosition<E>) p;
            int i = n.getIndex();
            int parentIndex = (i - 1) / 2;

            return tree.get(parentIndex);
        }

        public Iterable<Position<E>> children(Position<E> p) {
            List<Position<E>> list = new ArrayList<>();

            ArrayPosition<E> pos = (ArrayPosition<E>) p;
            int i = pos.getIndex();

            int leftIndex = 2 * i + 1;
            int rightIndex = 2 * i + 2;

            if (leftIndex < tree.size() && tree.get(leftIndex) != null) {
                list.add(tree.get(leftIndex));
            }
            if (rightIndex < tree.size() && tree.get(rightIndex) != null) {
                list.add(tree.get(rightIndex));
            }

            return list;
        }

        public Position<E> left(Position<E> p) {

            ArrayPosition<E> n = (ArrayPosition<E>) p;

            int index = n.getIndex();

            int leftIndex = 2 * index + 1;

            if (leftIndex < tree.size() && tree.get(leftIndex) != null) {
                return tree.get(leftIndex);
            }

            return null;

        }

        public Position<E> right(Position<E> p) {

            ArrayPosition<E> n = (ArrayPosition<E>) p;

            int index = n.getIndex();

            int rightIndex = 2 * index + 2;

            if (rightIndex < tree.size() && tree.get(rightIndex) != null) {
                return tree.get(rightIndex);
            }

            return null;
        }

        public boolean hasLeft(Position<E> p) {

            return left(p) != null;

        }

        public boolean hasRight(Position<E> p) {

            return right(p) != null;

        }

    }

    public static void main(String args[]) {

    }
}
