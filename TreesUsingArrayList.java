import java.util.*;

public class TreesUsingArrayList {

    public static interface Position<E> {
        E element();
    }

    public static class ArrayPosition<E> implements Position<E> {

        private int index;
        private E element;

        ArrayPosition(int index, E element) {
            this.index = index;
            this.element = element;
        }

        public void setElement(E element) {
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
            if (tree.isEmpty() || tree.get(0) == null)
                return null;
            return tree.get(0);
        }

        public Position<E> parent(Position<E> p) {
            ArrayPosition<E> n = (ArrayPosition<E>) p;
            int i = n.getIndex();
            if (i == 0)
                return null;
            int parentIndex = (i - 1) / 2;

            if (parentIndex < tree.size() && tree.get(parentIndex) != null) {
                return tree.get(parentIndex);
            }

            return null;

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

        public void addRoot(E e) {

            if (!tree.isEmpty()) {
                throw new IllegalStateException("Tree already has a root..!");
            }

            ArrayPosition<E> root = new ArrayPosition<>(0, e);

            tree.add(root);
            size++;

        }

        public void insertLeft(Position<E> p, E e) {
            ArrayPosition<E> n = (ArrayPosition<E>) p;

            int index = n.getIndex();
            if (tree.get(index) == null)
                throw new IllegalStateException("Invalid parent node");
            int leftIndex = index * 2 + 1;

            while (tree.size() <= leftIndex) {
                tree.add(null);
            }

            if (tree.get(leftIndex) != null) {
                throw new IllegalStateException("Left child already exists");
            }
            ArrayPosition<E> left = new ArrayPosition<>(leftIndex, e);

            tree.set(leftIndex, left);
            size++;

        }

        public void insertRight(Position<E> p, E e) {
            ArrayPosition<E> n = (ArrayPosition<E>) p;

            int index = n.getIndex();
            if (tree.get(index) == null)
                throw new IllegalStateException("Invalid parent node");
            int rightIndex = index * 2 + 2;

            while (tree.size() <= rightIndex) {
                tree.add(null);
            }

            if (tree.get(rightIndex) != null) {
                throw new IllegalStateException("Right child already exists");
            }
            ArrayPosition<E> right = new ArrayPosition<>(rightIndex, e);

            tree.set(rightIndex, right);
            size++;

        }

        public void replace(Position<E> p, E e) {
            ArrayPosition<E> arr = (ArrayPosition<E>) p;

            arr.setElement(e);
        }

        public void remove(Position<E> p) {
            ArrayPosition<E> arr = (ArrayPosition<E>) p;
            int index = arr.getIndex();

            if (hasLeft(p) || hasRight(p)) {
                throw new IllegalStateException("Cannot remove internal node");
            }

            tree.set(index, null);
            size--;
        }

    }

    public static void main(String args[]) {
        ArrayBinaryTree<Integer> ABT = new ArrayBinaryTree<>();

        ABT.addRoot(0);

        ABT.insertLeft(ABT.root(), 1);
        ABT.insertRight(ABT.root(), 2);

        for (Position<Integer> p : ABT.children(ABT.root())) {
            System.out.println(p.element());
        }
    }
}
