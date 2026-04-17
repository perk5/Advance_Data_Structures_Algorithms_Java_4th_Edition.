import java.util.Arrays;

public class ShrinkingArrayList {

    public static class IndexOutOfBoundException extends Exception {
        public IndexOutOfBoundException(String message) {
            super(message);
        }
    }

    public interface IndexList<E> {
        public int size();

        public boolean isEmpty();

        public void add(int r, E e) throws IndexOutOfBoundException;

        public E get(int r) throws IndexOutOfBoundException;

        public E remove(int r) throws IndexOutOfBoundException;

        public E set(int r, E e) throws IndexOutOfBoundException;

    }

    public static class ArrayIndexList<E> implements IndexList<E> {
        protected E[] A;

        protected int capacity = 16;
        private int size = 0;

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size <= 0;
        }

        public ArrayIndexList() {
            A = (E[]) new Object[capacity];
        }

        protected void checkIndex(int r, int n) {
            if (r < 0 || r >= n) {
                throw new IndexOutOfBoundsException();
            }
        }

        public E get(int r) throws IndexOutOfBoundException {
            checkIndex(r, size());
            return A[r];
        }

        public E set(int r, E e) throws IndexOutOfBoundException {
            checkIndex(r, size());
            A[r] = e;
            return A[r];
        }

        public void add(int r, E e) throws IndexOutOfBoundException {
            checkIndex(r, size() + 1);
            if (size == capacity) {
                capacity *= 2;
                E[] B = (E[]) new Object[capacity];
                for (int i = 0; i < size; i++) {
                    B[i] = A[i];
                }

            }
            for (int i = size - 1; i >= r; i--) {
                A[i + 1] = A[i];
            }

            A[r] = e;
            size++;
        }

        public E remove(int r) throws IndexOutOfBoundException {
            checkIndex(r, size());
            E temp = A[r];
            for (int i = r; i < size - 1; i++) {
                A[i] = A[i + 1];
            }
            size--;
            return temp;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");

            for (int i = 0; i < size; i++) {
                sb.append(A[i]);
                if (i < size - 1)
                    sb.append(", ");
            }

            sb.append("]");
            return sb.toString();
        }
    }

    public static class ShrinkingArrayL<E> extends ArrayIndexList<E> {

        public void shrinkToFit() {
            int n = size();
            E[] B = (E[]) new Object[n];
            for (int i = 0; i < n; i++) {
                B[i] = A[i];
            }
            A = B;
            capacity = n;
        }

    }

    public static void main(String[] args) throws IndexOutOfBoundException {
        ShrinkingArrayL<Integer> list = new ShrinkingArrayL<>();
        list.add(0, 10);
        list.add(1, 20);
        list.add(2, 30);
        list.add(3, 50);
        // list.shrinkToFit();
        System.out.println(list.capacity);
        System.out.println(list.toString());
    }
}