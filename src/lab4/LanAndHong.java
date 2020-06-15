package lab4;

import java.util.Scanner;

public class LanAndHong {

    static int count = 0;
    static boolean[][] data;
    static int num = 0;

    static int[][] useful_x = new int[65][4];
    static int[][] useful_y = new int[65][4];


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        data = new boolean[size][size];
        for (int i = 0;i < size;i++){
            String s = sc.next();
            for (int j = 0;j < size;j++){
                data[i][j] = s.charAt(j) == '.';
                if (data[i][j])
                    num++;
            }
        }

        DFS(0,0,1,0);

        System.out.println(count);
    }


    private static void DFS(int x,int y,int used,int index){

        data[x][y] = false;
        
        if (data[x][y] == data[data.length - 1][0]){
            if (used == num){
                count = count+1;
            }
            data[x][y] = true;
            return;
        }

        int only_x = -1;
        int only_y = -1;
        for (int i = 0;i < 4;i++){
            useful_x[index][i] = -1;
            useful_y[index][i] = -1;
        }
        if (x - 1 >= 0){
            if (data[x - 1][y]) {
                useful_x[index][0] = x - 1;
                useful_y[index][0] = y;
                if (out(x - 1,y) == 1) {
                    only_x = x - 1;
                    only_y = y;
                }
            }
        }
        if (x + 1 < data.length){
            if (data[x + 1][y]){
                useful_x[index][1] = x + 1;
                useful_y[index][1] = y;
                if (out(x + 1,y) == 1)
                    if (only_x == -1){
                        only_x = x + 1;
                        only_y = y;
                    }
                    else {
                        data[x][y] = true;
                        return;
                    }
            }
        }
        if (y - 1 >= 0){
            if (data[x][y - 1]){
                useful_x[index][2] = x;
                useful_y[index][2] = y - 1;
                if (out(x,y - 1) == 1)
                    if (only_x == -1){
                        only_x = x;
                        only_y = y - 1;
                    }
                    else {
                        data[x][y] = true;
                        return;
                    }
            }
        }
        if (y + 1 < data.length){
            if (data[x][y + 1]){
                useful_x[index][3] = x;
                useful_y[index][3] = y + 1;
                if (out(x,y + 1) == 1)
                    if (only_x == -1){
                        only_x = x;
                        only_y = y + 1;
                    }
                    else {
                        data[x][y] = true;
                        return;
                    }
            }
        }
        if (only_x != -1){

            DFS(only_x,only_y,used + 1,index + 1);
        }
        else {

            for (int i = 0;i < 4;i++) {
                if (useful_x[index][i] != -1) {
                    DFS(useful_x[index][i], useful_y[index][i], used + 1,index + 1);
                }
            }
        }
        data[x][y] = true;
    }

    private static int out(int x,int y){
        if (x == data.length - 1 && y == 0){
            return 2;
        }
        int cnt = 0;
        if (x - 1 >= 0){
            if (data[x - 1][y]) {
                cnt++;
            }
        }
        if (x + 1 < data.length){
            if (data[x + 1][y]){
                cnt++;
            }
        }
        if (y - 1 >= 0){
            if (data[x][y - 1]){
                cnt++;
            }
        }
        if (y + 1 < data.length){
            if (data[x][y + 1]){
                cnt++;
            }
        }
//
//
//        if (x - 1 >= 0 && y - 1 >= 0){
//            if (data[x - 1][y - 1])
//                cnt++;
//        }
//        if (x - 1 >= 0 && y + 1 < data.length){
//            if (data[x - 1][y + 1])
//                cnt++;
//        }
//        if (x + 1 < data.length && y - 1 >= 0){
//            if (data[x + 1][y - 1])
//                cnt++;
//        }
//        if (x + 1 < data.length && y + 1 < data.length){
//            if (data[x + 1][y + 1])
//                cnt++;
//        }
        return cnt;
    }

}
