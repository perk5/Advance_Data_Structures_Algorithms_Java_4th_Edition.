
class TowerOfHanoi {
    

    public void towerHanoi(int n, int start, int end) {
        int other = 0;
        if (n == 1) {
            print(start, end);
        } else {
            other = 6 - (start + end);
            towerHanoi(n - 1, start, other);
            print(start, end);
            towerHanoi(n - 1, other, end);
        }

    }

    public void print(int start, int end) {
        System.out.println("(" + start + "," + end + ")");
    }

    public static void main(String args[]) {

        TowerOfHanoi Toh = new TowerOfHanoi();

        Toh.towerHanoi(4, 1, 3);
    }
}