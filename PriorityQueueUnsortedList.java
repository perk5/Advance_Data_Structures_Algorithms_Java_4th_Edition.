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

        list.traverse();
        System.out.println();
        list.removeMin();
        System.out.println();

        list.traverse();
        System.out.println();
        System.out.println(list.min().getKey());
    }

}