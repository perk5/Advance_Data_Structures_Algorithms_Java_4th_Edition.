import java.util.*;

class BirthdayProbability {

    int[] birthday;

    BirthdayProbability(int[] n) {
        birthday = n;
    };

    public int[] printString() {
        Random rand = new Random();
        
        for (int i = 0; i < birthday.length; i++) {
            boolean foundDuplicate = false;
            int r = rand.nextInt(1, 366);
            for (int j = 0; j < i; j++) { 
                if (birthday[j] == r) {
                    foundDuplicate = true;
                    break;
                }
            }

            if (foundDuplicate) {
                System.out.println("found match.." + r);
            }

            birthday[i] = r;
        }
        return birthday;
    }
}

public class BirthdayParadox {

    public static void main(String args[]) {
        int[] arr = new int[50];

        BirthdayProbability b = new BirthdayProbability(arr);

        System.out.println(Arrays.toString(b.printString()));
    }

}
