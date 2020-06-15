package lab6;
import java.io.*;
import java.util.*;

public class Lab6_1 {

    static long path;
    static double log_path;
    static double len;
    static double base = Math.log(5);
    static int mod_num = 19260817;

    public static void main(String[] args) throws IOException {
        Reader sc = new Reader();
        PrintWriter out=new PrintWriter(System.out);

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

        out.close();
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
