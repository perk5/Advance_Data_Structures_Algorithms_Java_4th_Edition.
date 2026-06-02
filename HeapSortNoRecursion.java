import java.util.*;

public class HeapSortNoRecursion {

    public static class HeapSort {
        List<Integer> A = new ArrayList<>();

        public void initialHeap() {
            A.add(54);
            A.add(16);
            A.add(28);
            A.add(5);
            A.add(20);
        }

        public void buildMaxHeap() {
            int n = A.size();

            for (int i = n / 2 - 1; i >= 0; i--) {
                createHeap(A, i, n);
            }

            for (int i = n - 1; i > 0; i--) {

                int temp = A.get(0);
                A.set(0, A.get(i));
                A.set(i, temp);

                createHeap(A, 0, i);
            }

        }

        public void createHeap(List<Integer> arr, int i, int n) {

            while (true) {
                int largest = i;
                int left = i * 2 + 1;
                int right = i * 2 + 2;

                if (left < n && arr.get(left) > arr.get(largest)) {
                    largest = left;
                }

                if (right < n && arr.get(right) > arr.get(largest)) {
                    largest = right;
                }

                if (largest == i) {

                    break;
                }
                swapElement(i, largest);
                i = largest;
            }

        }

        public void swapElement(int i, int largest) {
            int temp = A.get(largest);
            A.set(largest, A.get(i));
            A.set(i, temp);
        }

        public void printHeap() {
            System.out.println(A.toString());
        }

    }

    public static void main(String args[]) {
        HeapSort HS = new HeapSort();
        HS.initialHeap();
        HS.buildMaxHeap();
        HS.printHeap();
    }
}