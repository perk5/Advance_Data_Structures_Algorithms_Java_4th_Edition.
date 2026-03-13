public class RowsColumnsQuad {

    public static int findInQuad(int[][] arr, int maxRow, int row, int col) {
        while (row != arr.length) {
            
            if (col >= arr[0].length) {
                return maxRow;

            } else {
                if (arr[row][col] == 0) {
                    row = row + 1;
                } else {
                    col = col + 1;
                    maxRow = row;
                }
            }

        }
        return maxRow;
    }

    public static void main(String args[]) {
        int arr[][] = {
                { 1, 1, 1, 1, 0 },
                { 1, 1, 1, 1, 0 },
                { 1, 0, 0, 0, 0 },
                { 1, 1, 1, 1, 0 },
                { 1, 1, 1, 1, 1 }
        };

        System.out.println(findInQuad(arr, 0, 0, 0));

    }
}

// public class RowsColumnsQuad {

//     public static int findRowWithMostOnes(int[][] arr) {

//         int n = arr.length;

//         int row = 0;
//         int col = n - 1;   // start from top-right
//         int maxRow = -1;

//         while (row < n && col >= 0) {

//             if (arr[row][col] == 1) {
//                 maxRow = row;   // update row with more 1s
//                 col--;          // move left
//             } else {
//                 row++;          // move down
//             }
//         }

//         return maxRow;
//     }

//     public static void main(String[] args) {

//         int arr[][] = {
//                 {1,1,1,1,0},
//                 {1,1,1,1,1},
//                 {1,0,0,0,0},
//                 {1,1,1,1,0},
//                 {1,1,1,1,0}
//         };

//         System.out.println(findRowWithMostOnes(arr));
//     }
// }