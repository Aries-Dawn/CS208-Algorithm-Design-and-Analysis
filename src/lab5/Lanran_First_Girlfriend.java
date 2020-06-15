package lab5;

import java.util.*;

public class Lanran_First_Girlfriend {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int size = sc.nextInt();
        while (size-- > 0){
            int count = 0;
            int lastEndTime = 0;
            int num = sc.nextInt();
            int[][] data = new int[num][2];
            for (int i = 0;i < num;i++){
                data[i][0] = sc.nextInt();
                data[i][1] = sc.nextInt();
            }
            quickSort(data,0,data.length - 1);
            for (int[] datum : data) {
                if (datum[0] >= lastEndTime) {
                    count++;
                    lastEndTime = datum[1];
                }
            }
            System.out.println(count);

        }

    }

    private static void print(int[][] data){
        for (int[] datum : data) {
            for (int i : datum) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    public static void quickSort(int[][] num, int low, int high){
        if (low < high){
            int p = position(num,low,high);
            quickSort(num,low,p - 1);
            quickSort(num,p + 1,high);
        }
    }

    private static int position(int[][] num, int low, int high){
        int positions = low;
        int positionNum = num[low][1];
        while (low < high){
            while (low < high && num[high][1] >= positionNum)
                high--;
            while (low < high && num[low][1] <= positionNum)
                low++;
            swap(num,low,high);
        }
        swap(num,positions,low);
        return low;
    }

    private static void swap(int[][] num, int low, int high){
        int[] temp = num[low];
        num[low] = num[high];
        num[high] = temp;
    }


}
