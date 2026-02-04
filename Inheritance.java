import java.math.BigInteger;

class Progression {
    protected BigInteger first;
    protected BigInteger current;

    Progression() {
        first = BigInteger.ZERO;
        current = BigInteger.ZERO;
    }

    public BigInteger firstValue() {
        current = first;
        return current;
    }

    protected BigInteger nextValue() {
        current = current.add(BigInteger.ONE);
        return current;
    }

    public void printProgression(int n) {
        // System.out.println(firstValue());

        for (int i = 1; i < n; i++) {
            System.out.println(nextValue());
        }
    }

}

class SquareRoot extends Progression {

    SquareRoot() {
        this(65536.0);
    }

    SquareRoot(double n) {
        first = current = BigInteger.valueOf((long) n);
    }

    protected BigInteger nextValue() {
        double temp = current.doubleValue();
        temp = Math.sqrt(temp);
        current = BigInteger.valueOf((long) temp); // truncate decimal
        return current;
    }
}

class NewClass extends Progression {

    NewClass() {
        this(BigInteger.valueOf(2), BigInteger.valueOf(200));
    }

    NewClass(BigInteger a, BigInteger b) {
        first = a;
        current = b;
    }

    protected BigInteger nextValue() {
        BigInteger previous = current;
        if (current.compareTo(first) > 0) {
            current = current.subtract(first);
        } else {
            current = first.subtract(current);
        }

        first = previous;
        return current.abs();

    }
}

class ArithProgression extends Progression {
    protected BigInteger inc;

    ArithProgression() {
        this(BigInteger.ONE);
    }

    ArithProgression(BigInteger increment) {
        inc = increment;
        first = current = BigInteger.ZERO;
    }

    protected BigInteger nextValue() {
        current = current.add(inc);
        return current;
    }
}

class GeomProgression extends Progression {

    protected BigInteger base;

    GeomProgression() {
        this(BigInteger.valueOf(2));
    }

    GeomProgression(BigInteger b) {
        base = b;
        first = BigInteger.ONE;
        current = first;
    }

    protected BigInteger nextValue() {
        current = current.multiply(base) ;
        return current;
    }
}

class Fibonacci extends Progression {
    BigInteger prev;

    Fibonacci() {
        this(BigInteger.ZERO, BigInteger.ONE);
    }

    Fibonacci(BigInteger value1, BigInteger value2) {
        first = value1;
        prev = value2.subtract(value1) ;
    }

    protected BigInteger nextValue() {

        BigInteger temp = prev;
        prev = current;
        current =  current.add(temp);
        return current;
    }
}

public class Inheritance {
    public static void main(String args[]) {
        Progression prog;

        // System.out.println("Arithmetic progression with default increment : ");
        prog = new SquareRoot();
        prog.printProgression(8);
        // prog.printProgression(10);
        // prog = new ArithProgression();
        // prog.printProgression(10);
        // System.out.println("Arithmetic progression with increment 5: ");
        // prog = new ArithProgression(5);
        // prog.printProgression(10);
        // System.out.println("Geometric progression with default base : ");
        // prog = new GeomProgression();
        // prog.printProgression(10);
        // prog = new Fibonacci(2,2);
        // prog.printProgression(10);
    }
}
