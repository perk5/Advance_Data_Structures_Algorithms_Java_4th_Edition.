import java.util.Random;
class Punishment {
    
    String[] s = {
        " I will never spam my friends again - Type 1.", 
        " I will never spam my friends again - Type 2.",
        " I will never spam my friends again - Type 3.", 
        " I will never spam my friends again - Type 4.",
        " I will never spam my friends again - Type 5.", 
        " I will never spam my friends again - Type 6.",
        " I will never spam my friends again - Type 7.", 
        " I will never spam my friends again - Type 8."
    };
    

    public void printString(){
        Random rand = new Random();
        for(int i = 1; i < 101; i++){
            int r = rand.nextInt(8);
            System.out.println(i + s[r]);
        }
    }
}

public class StudentPunishment {
    public static void main(String args[]) {
        Punishment p = new Punishment();
        p.printString();
    }
}
