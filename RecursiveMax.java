public class RecursiveMax {

    public int findmax(int[] arr, int maximum, int nextIndex) {
        int max = maximum;
        if (nextIndex > arr.length -1) {
            return max;
        }
        int currentIndex = arr[nextIndex];

        if (currentIndex > maximum) {
            max = currentIndex;
        }
        nextIndex++;

        return findmax(arr, max, nextIndex);

    }

    public void main() {
        int[] numArray = new int[] { 4, 11, 3, 8, 6 ,10};
        System.out.println(findmax(numArray, 0, 0));
    }
}
