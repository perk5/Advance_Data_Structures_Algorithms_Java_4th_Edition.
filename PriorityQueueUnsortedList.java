import java.util.*;

public class PriorityQueueUnsortedList {

    public interface Entry<K, V> {
        K getKey();

        V getValue();
    }

    public interface IPriorityQueue<K, V> {
        public int size();

        public boolean isEmpty();

        public Entry<K, V> min();

        public void insert(K key, V value);

        public Entry<K, V> removeMin();

    }

    public static class UnSortedList<K extends Comparable<K>, V>
            implements IPriorityQueue<K, V> {

        List<MyEntry<K, V>> list = new ArrayList<>();

        public int size() {
            return list.size();
        }

        public boolean isEmpty() {
            return size() == 0;
        }

        public Entry<K, V> min() {

            if (isEmpty())
                return null;

            Entry<K, V> min = list.get(0);

            for (Entry<K, V> e : list) {
                if (((Comparable<K>) e.getKey()).compareTo(min.getKey()) < 0) {
                    min = e;
                }
            }
            return min;

        }

        public void insert(K key, V value) {
            MyEntry<K, V> newEntry = new MyEntry<>(key, value);

            list.add(newEntry);

        }

        public Entry<K, V> removeMin() {

            if (isEmpty())
                return null;

            int minIndex = 0;

            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).getKey().compareTo(list.get(minIndex).getKey()) < 0) {
                    minIndex = i;
                }
            }

            return list.remove(minIndex);
        }

        public static class MyEntry<K, V> implements Entry<K, V> {
            K key;
            V value;

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

        public void maxHeap() {
            int n = size();

            for (int i = n / 2 - 1; i >= 0; i--) {
                heapify(i , n);
            }

            for (int i = n - 1; i > 0 ; i--){
                swap(0, i);
                heapify(0, i);
            }

        }

        public void heapify(int currentIndex, int length) {

            while (true) {
                int highest = currentIndex;
                int left = currentIndex * 2 + 1;
                int right = currentIndex * 2 + 2;
                if (left < length && list.get(currentIndex).getKey().compareTo(list.get(left).getKey()) < 0) {
                    highest = left;
                }

                if (right < length && list.get(highest).getKey().compareTo(list.get(right).getKey()) < 0) {
                    highest = right;
                }

                if (highest == currentIndex) {
                    break;
                }

                swap(currentIndex, highest);
                currentIndex = highest;
               
            }
        }

        public void swap(int current, int highest) {
            MyEntry<K, V> temp = list.get(current);
            list.set(current, list.get(highest));
            list.set(highest, temp);
        }

        public void traverse() {
            for (Entry<K, V> e : list) {
                System.out.println(e.getKey());
            }
        }

    }

    public static void main(String args[]) {
        UnSortedList<Integer, String> list = new UnSortedList<>();

        list.insert(24, "Perk");
        list.insert(14, "Raj");

        list.insert(94, "Rama");
        list.insert(11, "James");
        list.insert(15, "John");

        list.insert(2, "Cena");
        list.insert(9, "Kara");
        list.insert(16, "Kayla");

        list.traverse();
        System.out.println();

        list.traverse();
        System.out.println();
        System.out.println(list.min().getKey());
        list.maxHeap();
        System.out.println();
        list.traverse();

    }

}