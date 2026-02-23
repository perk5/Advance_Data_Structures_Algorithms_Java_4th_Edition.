import java.util.*;

public class Matrix<T extends Number> {

    public static void addMultiply() {
        Number[][] a = new Number[1][3];
        Number[][] b = new Number[3][2];
        Number[][] answer = new Number[a.length][b[0].length];
        Random rand = new Random(2);

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = rand.nextInt(0, 9);
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();

        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                b[i][j] = rand.nextInt(0, 9);
                System.out.print(b[i][j] + " ");
            }
            System.out.println();
        }

        // for (int i = 0; i < b[0].length; i++) {
        //     int count = 0;
        //     for (int j = 0; j < b.length; j++) {
        //         for (int k = 0; k < 1; k++) {
        //             count = count + (a[k][j] * b[j][i]);
        //         }
        //     }
        //     answer[0][i] = count;
        // }

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                double sum = 0;
                for (int k = 0; k < a[0].length; k++) {
                    sum += a[i][k].doubleValue() * b[k][j].doubleValue();
                }
                answer[i][j] = sum;
            }
        }

         for (int i = 0; i < answer.length; i++) {
            for (int j = 0; j < answer[0].length; j++) {
                
                System.out.print(answer[i][j] + " ");
            }
            System.out.println();
        }

    }

    public static void main(String args[]) {
        addMultiply();
    }
}
