import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        int num = size;
        while (size > 0){
            System.out.print(num - size + "、 ");
            size--;
            long start = System.currentTimeMillis();
            long sum = 0;
            int n = sc.nextInt();
            int[] length = new int[11];
            String[] data = new String[n];
            for (int i = 0;i < n;i++){
                String temp = sc.next();
                data[i] = temp;
                length[temp.length()]++;
            }
            for (int i = 0;i < n;i++){
                //find smaller item
                int len = data[i].length();
                for (int j = len - 1;j > 0;j--){
                    if (length[j] != 0){
                        long pre = 0;
                        int sub = len - j;
                        for (int k = 0;k < sub;k++){
                            pre += (long)data[i].charAt(k) - '0';
                            pre %= 998244353;
                            pre *= (long) 10;
                        }
                        pre /= 10;

                        long temp = pre;
                        //as a
                        for (int k = sub;k < len;k++){
                            pre *= (long) 10;
                            pre += (long)data[i].charAt(k) - '0';
                            pre %= 998244353;
                            pre *= (long) 10;
                        }
                        sum += ((long)length[j] * (pre % 998244353)) % 998244353;
                        sum %= 998244353;

                        pre = temp;
                        //as b
                        for (int k = sub;k < len;k++){
                            pre *= (long) 100;
                            pre += (long)data[i].charAt(k) - '0';
                            pre %= 998244353;
                        }
                        sum += ((long)length[j] * (pre % 998244353)) % 998244353;
                        sum %= 998244353;

                    }
                }
                //find larger item
                int allBiggerNum = 0;
                for (int j = len;j < 11;j++){
                    allBiggerNum += length[j];
                }
                long pre = 0;
                //as a
                pre += (long)data[i].charAt(0) - '0';
                pre %= 998244353;
                pre *= (long) 10;
                for (int k = 1;k < len;k++){
                    pre *= (long) 10;
                    pre += (long)data[i].charAt(k) - '0';
                    pre %= 998244353;
                    pre *= (long) 10;
                }
                sum += ((long)allBiggerNum * (pre % 998244353)) % 998244353;
                sum %= 998244353;

                //as b
                pre /= 10;

                sum += ((long)allBiggerNum * (pre % 998244353)) % 998244353;
                sum %= 998244353;
            }

            System.out.print(sum % 998244353 + "    ");

            long end = System.currentTimeMillis();
            System.out.println(end - start);
        }

    }


    public static void generateList(int n, long[] list) {
        for (int i = 0; i < n; i++) {
            list[i] = i;
        }
        // shuffle
        Random rnd = new Random();
        for (int i = list.length; i > 1; i--) {
            int j = rnd.nextInt(i);
            long temp = list[j];
            list[j] = list[i - 1];
            list[i - 1] = temp;
        }
    }


    private static void mergeSort(long[] data, long n){

        if (n > 1){
            long[] A,B;
            A = Arrays.copyOfRange(data,0, (int) (n / 2));
            B = Arrays.copyOfRange(data,(int)n / 2,(int)n);
            mergeSort(A,n / 2);
            mergeSort(B,n - n / 2);
            long[] nums = merge(A,(int)n / 2,B, (int) ((int)n - n / 2));
            int k = 0;
            for (long num : nums) {
                data[k] = num;
                k += 1;
            }

        }

    }

    private static long[] merge(long[] A,int A_size, long[] B, int B_size){
        long[] num = new long[A_size + B_size];
        int i = 0,j = 0;
        for (int k = 0;k < num.length;k++){
            if (i < A.length && j < B.length && A[i] <= B[j]){
                num[k] = A[i];
                i += 1;
            }
            else if (i < A.length && j < B.length && A[i] > B[j]){
                num[k] = B[j];
                j += 1;
            }
            else if (i < A.length || j < B.length){
                if (i >= A.length){
                    num[k] = B[j];
                    j += 1;
                }
                else if (j >= B.length){
                    num[k] = A[i];
                    i += 1;
                }

            }
        }
        return num;
    }
}
