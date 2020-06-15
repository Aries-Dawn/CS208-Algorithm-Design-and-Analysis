import java.util.Scanner;

public class assignment3_2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        String toPrint = "";
        for (int i = 0; i < n; i++) {
            int N = input.nextInt();
            long K = input.nextLong();
            int count = 0;
            while (true) {
                if ((K & (K - 1)) == 0) {
                    if ((K & 0x5555555555555555L) == K) break;
                    else if ((K & 0x5555555555555555L) == 0) {
                        count++;
                        break;
                    }
                } else {
                    K = (K % getPower(K));
                    count++;
                    if (K == 1) break;
                }
            }
            if (count % 2 == 1) toPrint += "1";
            else toPrint += "0";
        }
        for (int i = 0; i < toPrint.length(); i++) {
            System.out.println(toPrint.charAt(i));
        }
    }

    private static long getPower(long K) {
        K |= K >> 1;
        K |= K >> 2;
        K |= K >> 4;
        K |= K >> 8;
        K |= K >> 16;
        K |= K >> 32;
        return (K + 1) >> 1;
    }
}
