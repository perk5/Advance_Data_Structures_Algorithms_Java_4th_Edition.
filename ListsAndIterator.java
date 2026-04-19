import java.util.*;

public class ListsAndIterator {
    public static class Pair {
        int x;
        int y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Triple {
        int x;
        int y;
        int z;

        Triple(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public String toString() {
            return "(" + x + "," + y + "," + z + ")";
        }
    }

    public static class Compare {
        public List<Triple> join(List<Pair> A, List<Pair> B) {

            List<Triple> result = new ArrayList<>();

            Iterator<Pair> itA = A.iterator();

            while (itA.hasNext()) {

                Pair a = itA.next();

                Iterator<Pair> itB = B.iterator();

                while (itB.hasNext()) {

                    Pair b = itB.next();

                    if (a.y == b.x) {
                        result.add(new Triple(a.x, a.y, b.y));
                    }

                }
            }
            return result;
        }

    }

    public static void main(String[] args) {
        List<Pair> A = new ArrayList<>();
        List<Pair> B = new ArrayList<>();

        A.add(new Pair(1, 2));
        A.add(new Pair(3, 5));

        B.add(new Pair(2, 7));
        B.add(new Pair(2, 8));
        B.add(new Pair(5, 9));
        Compare c = new Compare();
        List<Triple> result = c.join(A, B);
        for (Triple t : result) {
            System.out.println(t);
        }

    }

}