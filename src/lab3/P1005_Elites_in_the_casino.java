package lab3;

import java.sql.Time;
import java.util.Scanner;

public class P1005_Elites_in_the_casino {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

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

        System.out.println(sum % 998244353);
    }
}
