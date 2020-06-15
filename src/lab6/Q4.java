package lab6;

import java.util.Arrays;
import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String in = sc.nextLine();
        in = in.replaceAll(" " , "");
        int len = in.length();
        int size = (int)Math.sqrt(len) + 1;
        int num = 0;
        int index = 0;

        char[][] matrix = new char[size * 2][size * 2];
        for (char[] chars : matrix) {
            Arrays.fill(chars, ' ');
        }
        int up_right = 1;
        int down_left = up_right + 1;
        int a = size - 1,b = size - 1;
        int up_bound = a;
        int bottom_bound = a;
        int right_bound = b;
        int left_bound = b;
        flag:
        while (true){
            for (int i = 0;i < up_right;i++){
                matrix[a][b] = in.charAt(index);
                a--;
                index++;
                index %= len;
                num++;
                if (num < len && up_bound > a)
                    up_bound = a;
                if (num >= len && a < up_bound)
                    break flag;
            }
            for (int i = 0;i < up_right;i++){
                matrix[a][b] = in.charAt(index);
                b++;
                index++;
                index %= len;
                num++;
                if (num < len && right_bound < b)
                    right_bound = b;
                if (num >= len && b > right_bound)
                    break flag;
            }
            up_right += 2;
            for (int i = 0;i < down_left;i++){
                matrix[a][b] = in.charAt(index);
                a++;
                index++;
                index %= len;
                num++;
                if (num < len && bottom_bound < a)
                    bottom_bound = a;
                if (num >= len && a > bottom_bound)
                    break flag;
            }
            for (int i = 0;i < down_left;i++){
                matrix[a][b] = in.charAt(index);
                b--;
                index++;
                index %= len;
                num++;
                if (num < len && left_bound > b)
                    left_bound = b;
                if (num >= len && b < left_bound)
                    break flag;
            }
            down_left += 2;
        }
        boolean out;
        for (char[] chars : matrix) {
            out = false;
            for (char aChar : chars) {
                if (aChar != ' ') {
                    System.out.print(aChar);
                    out = true;
                }
            }
            if (out)
                System.out.println();
        }
    }
}
