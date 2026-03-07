public class RecursiveBaseTwo {


    public static int baseTwo(int target, int count){

        if(target <= 1){
            return count;
        }

        return baseTwo(target / 2 , count + 1);

    }

    public static void main(String args[]){
        System.out.println(baseTwo(2, 0));
    }
}
