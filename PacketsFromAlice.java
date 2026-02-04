import java.util.*;

class Alice{
    int packet;

    Alice(int p){
        this.packet = p;
    }

    // void CreatePacket(){
    //     System.out.println("Created a new Package with value" + packet);
    // }
}

class Internet{

    int message;

    Internet(Alice a){
        this.message = a.packet;
    }
}

class Bob{
    List<Integer> arr = new ArrayList<>();

    Bob(Internet i){
        arr.add(i.message);
    }

    void PrintMessages(){
        for(int i: arr){
            System.out.println(i);
        }
    }
}


public class PacketsFromAlice {
    public void main(){
       Alice a = new Alice(1); 
       Internet i = new Internet(a);
       
       Bob b = new Bob(i);
       b.PrintMessages();

    }
}
