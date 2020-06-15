package lab9;

public class test {
    public static void main(String[] args) throws InterruptedException {
        VC_String.in = false;
        long sum = VC_String.de(2);
        System.out.println(sum);
        VC_String.in = false;
        sum = VC_String.de(6);
        System.out.println(sum);

    }
}
