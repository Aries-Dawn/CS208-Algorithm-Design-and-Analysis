package lab5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Lanran_Second_Girlfriend {

    static int count = 0;
    static int time_size;
    static int temp = 1;
    static int num = 0;
    static int left = 0;
    static int right = 0;
    static int mid = 0;
    static PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.end - o2.end;
        }
    });

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int num = sc.nextInt();

        Node[] data = new Node[num];
        int begin = 0;
        int end = 0;
        int earliestStart = 10000;
        int leastEnd = 0;
        for (int i = 0;i < num;i++){

            begin = sc.nextInt();
            end = sc.nextInt();
            if (begin < earliestStart)
                earliestStart = begin;
            if (end > leastEnd)
                leastEnd = end;

            data[i] = new Node(begin,end);
        }
        time_size = leastEnd + 1;
        Arrays.sort(data, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.begin - o2.begin;
            }
        });

        search(data,0,(leastEnd - earliestStart + 1) / num);
        System.out.println(count);
    }

    private static void search(Node[] data,int begin,int end){
        right = begin;
        left = end;
        while (true){
            if (right > left){
                break;
            }
            mid = (left + right) / 2;
            if (check(data,mid))
                right = mid + 1;
            else
                left = mid - 1;

        }

    }

    private static boolean check(Node[] data,int set){
        temp = 1;
        clear(data);
        queue.clear();
        for (temp = 1;temp < time_size;temp++){
            for (int i = num;i < data.length;i++){
                if (temp >= data[i].begin){
                    if (temp == data[i].begin)
                        queue.add(data[i]);
                }
                else break;
            }
            if (queue.peek() == null)
                continue;
            if (queue.peek().end < temp && queue.peek().num < set)
                return false;
            if (queue.peek().begin <= temp && queue.peek().num < set){
                queue.peek().num++;
            }
            if (queue.peek().num == set)
                queue.poll();
        }
        if (queue.isEmpty()){
            count = set;
            return true;
        }
        return false;
    }

    private static class Node{
        int begin;
        int end;
        int num;

        Node(int begin,int end){
            this.begin = begin;
            this.end = end;
        }
    }

    private static void clear(Node[] data){
        for (Node s : data)
            s.num = 0;
    }

}
