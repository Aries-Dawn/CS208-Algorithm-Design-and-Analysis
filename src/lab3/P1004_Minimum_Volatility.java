package lab3;

import java.util.LinkedList;
import java.util.Scanner;

public class P1004_Minimum_Volatility {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Node[] bucket = new Node[10000001];
        int N = sc.nextInt();
        if (N == 0) {
            System.out.println(0);
            return;
        }
        Node[] data = new Node[N];
        for (int i = 0;i < N;i++){
            data[i] = new Node();
        }
        for (int i = 0;i < N;i++){
            int input = sc.nextInt();
            data[i].value = input;
            if (bucket[input] == null)
                bucket[input] = data[i];
        }
        System.gc();
        Node head = new Node();
        head.value = -1;
        Node last = head;
        for (Node temp : bucket){
            if (temp != null){
                last.next = temp;
                temp.pre = last;
                last = temp;
            }
        }
        Node newOne = new Node();
        newOne.value = -1;
        last.next = newOne;
        newOne.pre = last;

        int sum = 0;
        for (int i = N - 1;i > 0;i--){
            if (data[i].pre != null && data[i].next != null) {
                int pre = data[i].value - data[i].pre.value;
                int next = data[i].next.value - data[i].value;
                if (data[i].next.value == -1)
                    sum += pre;
                else if (data[i].pre.value == -1)
                    sum += next;
                else {
                    next = Math.abs(data[i].value - data[i].next.value);
                    sum += Math.min(pre, next);
                }
                data[i].next.pre = data[i].pre;
                data[i].pre.next = data[i].next;
            }
        }
        sum += data[0].value;
        System.out.println(sum);



    }

    private static class Node{
        int value;
        Node pre;
        Node next;
    }

}
