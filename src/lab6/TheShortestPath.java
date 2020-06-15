package lab6;

import java.util.*;

public class TheShortestPath {

    static long path;
    static double log_path;
    static double len;
    static double base = Math.log(5);
    static int mod_num = 19260817;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a,b,weight;
        int N = sc.nextInt();
        int m = sc.nextInt();
        Node[] point = new Node[N + 1];
        for (int i = 0;i < N + 1;i++){
            point[i] = new Node();
            point[i].value = i;
        }
        for (int i = 0;i < m;i++){
            a = sc.nextInt();
            b = sc.nextInt();
            weight = sc.nextInt();
            edge ed = new edge();
            ed.weight = weight;
            ed.next = b;
            point[a].edges.add(ed);
        }

        DiJi(1,N,point);
        System.out.println(point[N].length % mod_num);
    }


    public static void DiJi(int begin, int N, Node[] point){
        Queue<Node> queue = new PriorityQueue<>((o1, o2) -> (int)(o1.log_num - o2.log_num));
        point[begin].length = 1;
        point[begin].log_num = 1;
        queue.add(point[begin]);
        while (!queue.isEmpty()){
            Node temp = queue.poll();
            if (temp.value == N)
                break;
            for (int i = 0;i < temp.edges.size();i++){
                path = (temp.length % mod_num) * (temp.edges.get(i).weight % mod_num);
                path %= mod_num;
                log_path = temp.log_num +
                        Math.log(temp.edges.get(i).weight) / base;
                len = point[temp.edges.get(i).next].log_num;
                if (len == -1 || len > log_path){
                    point[temp.edges.get(i).next].length = path;
                    point[temp.edges.get(i).next].log_num = log_path;
                    queue.add(point[temp.edges.get(i).next]);
                }
            }

        }
    }

    static class edge{
        long weight;
        int next;
//
//        edge(int weight,Node next){
//            this.weight = weight;
//            this.next = next;
//        }
    }

    static class Node{
        int value;
        double log_num = -1;
        long length = -1;
        ArrayList<edge> edges = new ArrayList<>();

//        Node(int value){
//            this.value = value;
//        }

    }
}
