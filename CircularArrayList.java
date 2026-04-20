import java.util.*;

public class CircularArrayList {

    public interface Circular<E> {
        void addFirst(E element);

        void addLast(E element);

        E removeFirst();

        E removeLast();

        int size();

        boolean isEmpty();
    }

    public static class CircularList<E> implements Circular<E> {

        int size = 0;
        int capacity = 4;
        int front = 0;
        E[] l;

        @SuppressWarnings("unchecked")
        public CircularList() {
            l = (E[]) new Object[capacity];
        }

        @SuppressWarnings("unchecked")
        public void resize() {
            capacity = capacity * 2;

            E[] newarr = (E[]) new Object[capacity];

            for (int i = 0; i < size; i++) {
                newarr[i] = l[(front + i) % l.length];
            }
            l = newarr;
            front = 0;
        }

        public void addFirst(E element) {
            if (size == capacity) {
                resize();
            }

            front = (front - 1 + capacity) % capacity;
            l[front] = element;

            size++;

        }

        public void addLast(E element) {
            if (size == capacity) {
                resize();
            }

            l[(front + size) % capacity] = element;
            size++;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public E removeFirst() {

            if (size == 0) {
                throw new NoSuchElementException();
            }
            E removed = l[front];
            front = (front + 1) % capacity;
            size--;

            return removed;

        }

        public E removeLast() {

            if (size == 0) {
                throw new NoSuchElementException();
            }

            int lastIndex = (front + size - 1) % capacity;

            E removed = l[lastIndex];

            l[lastIndex] = null;

            size--;

            return removed;

        }

    }

    public static void main(String[] args) {

        CircularList<Integer> c = new CircularList<>();
        c.addFirst(4);
        c.addFirst(8);

    }

}