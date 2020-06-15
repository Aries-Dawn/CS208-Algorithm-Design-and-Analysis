package lab7;

import java.io.*;
import java.util.*;

public class TheGreatMystery {
    static int target;
    static Edge ee ;
    static long sum = 0;
    public static void main(String[] args) throws IOException {
        Reader sc = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] points = new int[n * m];
        Edge[][] eds = new Edge[4][n * m];
        for (int i = 0;i < points.length;i++){
                points[i] = sc.nextInt();
        }
        int num;
        for (int i = 0;i < points.length;i++){
            num = 0;
            if ((i + 1) % m > i % m) {
                target = points[i] ^ points[i + 1];
                eds[num][i] = new Edge(i,target,i + 1);
                num++;
            }
            if (i - 1 >= 0 && (i - 1) % m < i % m) {
                target = points[i] ^ points[i - 1];
                eds[num][i] = new Edge(i,target,i - 1);
                num++;
            }
            if (i + m < n * m) {
                target = points[i] ^ points[i + m];
                eds[num][i] = new Edge(i,target,i + m);
                num++;
            }
            if (i - m >= 0) {
                target = points[i] ^ points[i - m];
                eds[num][i] = new Edge(i,target,i - m);
            }
        }
        prim(points,eds);
        System.out.println(sum);
        out.close();
    }


    static class Edge{
        int weight;
        int first;
        int next;

        Edge(int first, int weight, int next){
            this.first = first;
            this.weight = weight;
            this.next = next;
        }
    }


    public static void prim(int[] point,Edge[][] eds){
        Queue<Edge> queue = new PriorityQueue<>((o1, o2) -> (int)(o1.weight - o2.weight));
        point[0] = -1;
        if (eds[0][0] != null)
            queue.add(eds[0][0]);
        if (eds[1][0] != null)
            queue.add(eds[1][0]);
        while (!queue.isEmpty()){
            ee = queue.poll();

            if (point[ee.next] == -1)
                continue;

            sum += ee.weight;
            point[ee.next] = -1;
            for (int i = 0;i < 4;i++){
                if (eds[i][ee.next] == null)
                    break;
                if (point[eds[i][ee.next].next] != -1){
                    queue.add(eds[i][ee.next]);
                }
            }

        }

    }


    static class Reader
    {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException
        {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1)
            {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do
            {
                ret = ret * 10 + c - '0';
            }  while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException
        {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException
        {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');

            if (c == '.')
            {
                while ((c = read()) >= '0' && c <= '9')
                {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }

}




//
//    static class edge{
//        int pre_x,pre_y;
//        long weight;
//        int next_x,next_y;
//
//        edge(int pre_x,int pre_y,int,){
//
//        }
//    }
//
//    static class int{
//        int magic;
//        int i,j;
//        long pay = max;
//        edge[] next = new edge[4];
//    }