package lab4;

import java.util.*;

public class Yu_Gi_Oh {

    int[] score;
    int[] temp_score;
    int[] minus_score;
    int[] reverse;
    int draw;
    int win_and_defeat;
    int allScore = 0;
    static HashMap<Long, Long> map = new HashMap<>();

    public static void main(String[] args) {

        Yu_Gi_Oh y = new Yu_Gi_Oh();
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();


        int[] score = new int[size];
        y.score = new int[size];
        y.temp_score = new int[size];
        y.minus_score = new int[size];
        y.reverse = new int[size];
        for (int i = 0;i < size;i++){
            score[i] = sc.nextInt();
            y.allScore += score[i];
        }

        int num = (size * (size - 1) / 2);

        y.draw = num * 3 - y.allScore;

        y.win_and_defeat = y.allScore - (y.draw * 2);

        Arrays.sort(score);
        for (int i = size - 1;i >= 0;i--){
            y.score[size - i - 1] = score[i];
        }


        System.out.println(y.DFS(0,1));


    }

    private long DFS(int x,int y){

        long count = 0;

        if (x == score.length - 1){
            int temp_sum = 0;
            for (int i = 0;i < score.length;i++){
                temp_sum += temp_score[i];
            }

            if (temp_sum == allScore)
                return 1;
            else
                return 0;
        }


        if (temp_score[x] + 3 * (score.length - y + 1) < score[x])
            return 0;

        if(y >= score.length){

            if (temp_score[x] != score[x])
                return 0;

            for (int i = 0;i < score.length;i++){
                minus_score[i] = 0;
            }
            for (int i = 0;i < score.length;i++){
                minus_score[i] = score[i] - temp_score[i];
            }

            Arrays.sort(minus_score,x + 1,minus_score.length);
            for (int i = 0;i < minus_score.length;i++) {
                reverse[i] = minus_score[minus_score.length - 1 - i];
            }

            long sum = 0;
            for (int i = 0;i < minus_score.length - x;i++){
                sum = sum * 29 + reverse[i] + 1;
            }

            if (map.containsKey(sum))
                return map.get(sum);
            else{
                long temp = DFS(x + 1,x + 2);
                map.put(sum,temp);
                return temp;
            }
        }

        if (win_and_defeat > 0 && temp_score[x] + 3 <= score[x]){
            win_and_defeat -= 1;
            temp_score[x] += 3;
            count += DFS(x,y + 1);
            win_and_defeat += 1;
            temp_score[x] -= 3;
        }

        if (win_and_defeat > 0 && temp_score[x] + 2 <= score[x] && temp_score[y] + 1 <= score[y]){
            win_and_defeat -= 1;
            temp_score[x] += 2;
            temp_score[y] += 1;
            count += DFS(x,y + 1);
            win_and_defeat += 1;
            temp_score[x] -= 2;
            temp_score[y] -= 1;
        }
        if (draw > 0 && temp_score[x] + 1 <= score[x] && temp_score[y] + 1 <= score[y]){
            temp_score[x] += 1;
            temp_score[y] += 1;
            draw -= 1;
            count += DFS(x,y + 1);
            temp_score[x] -= 1;
            temp_score[y] -= 1;
            draw += 1;
        }
        if (win_and_defeat > 0 && temp_score[x] + 1 <= score[x] && temp_score[y] + 2 <= score[y]){
            temp_score[x] += 1;
            temp_score[y] += 2;
            win_and_defeat -= 1;
            count += DFS(x,y + 1);
            temp_score[x] -= 1;
            temp_score[y] -= 2;
            win_and_defeat += 1;
        }
        if (win_and_defeat > 0 && temp_score[y] + 3 <= score[x]){
            win_and_defeat -= 1;
            temp_score[y] += 3;
            count += DFS(x,y + 1);
            win_and_defeat += 1;
            temp_score[y] -= 3;
        }

        return count % 998244353;
    }



}
