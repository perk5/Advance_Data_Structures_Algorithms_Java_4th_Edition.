public class ProductTwoPositive {

    public static int ProductWithoutMultiplication(int m, int n) {

        if (n == 1) {
            return m;
        }

        return m + ProductWithoutMultiplication(m, n - 1);

    }

    public static void main(String args[]) {
        System.out.println(ProductWithoutMultiplication(6, 5));
    }
}
