package lab6;
//快读模板1：更快，但没有next()用于读字符串

import java.io.*;
import java.util.*;

public class FindingVince {

    static int first;
    static int second;
    static int weight;
    static long path;
    static long len;
    static long diff;
    static int a, b;
    public static void main(String[] args) throws IOException {
        Reader sc=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        int n = sc.nextInt();
        int m = sc.nextInt();
        Node[] vertexes = new Node[n + 1];
        for (int i = 0;i <= n;i++){
            vertexes[i] = new Node(i);
        }

        for (int i = 0;i < m;i++){
            first = sc.nextInt();
            second = sc.nextInt();
            weight = sc.nextInt();
            vertexes[first].edges.add(new edge(weight,second));
        }
        for (int i = 1;i <= n;i++){
            vertexes[i].a = sc.nextInt();
            vertexes[i].b = sc.nextInt();
        }
        DiJi(1,n,vertexes);
        System.out.println(vertexes[n].length);

        out.close();
    }

    public static void DiJi(int begin, int N, Node[] point){
        Queue<Node> queue = new PriorityQueue<>((o1, o2) -> (int)(o1.length - o2.length));
        point[begin].length = 0;
        queue.add(point[begin]);
        while (!queue.isEmpty()){
            Node temp = queue.poll();
//            if (temp.value == N)
//                break;
            for (int i = 0;i < temp.edges.size();i++){
                a = point[temp.edges.get(i).next].a;
                b = point[temp.edges.get(i).next].b;
                diff = (temp.length + temp.edges.get(i).weight) % (a + b);
                if (diff <= a)
                    path = (temp.length + temp.edges.get(i).weight) + (a - diff);
                else
                    path = temp.length  + temp.edges.get(i).weight ;
                len = point[temp.edges.get(i).next].length;
                if (len == -1 || len > path){
                    point[temp.edges.get(i).next].length = path;
                    queue.add(point[temp.edges.get(i).next]);
                }
            }

        }
    }
    static class edge{
        long weight;
        int next;

        edge(int weight,int next){
            this.weight = weight;
            this.next = next;
        }
    }

    static class Node{
        int value;
        int a;
        int b;
        long length = -1;
        ArrayList<edge> edges = new ArrayList<>();

        Node(int value){
            this.value = value;
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
