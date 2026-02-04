class A{
    int x = 0;
}

class B extends A{
    int x = 1;
}

class C extends B{
    int x = 2;

    public void PrintInformation(){
        ((A)this).x = 5;
        System.out.println(((A)this).x);
    }
}

public class Practicing {
    public void main(String args[]){
        C excess = new C();
        excess.PrintInformation();
    }
}
