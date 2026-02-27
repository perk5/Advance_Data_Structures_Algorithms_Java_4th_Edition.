public class PowerRecursive {

    public static int recurse(int x, int n){

        if(n == 1){
            return x;
        }
        return x = x * recurse(x , n - 1);
        
    }

    public static void main(String args[]){
        System.out.println(recurse(2, 4));
    }
}
