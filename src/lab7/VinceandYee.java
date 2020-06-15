package lab7;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;


public class VinceandYee {

    static int max = Integer.MAX_VALUE;
    static Edge ee ;
    static long sum = 0;
    static Edge min = new Edge();
    static int count;
    
    public static void main(String[] args) throws IOException {
        Reader sc = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = sc.nextInt();
        int[] point = new int[n + 1];
        Edge[][] eds = new Edge[n + 1][n + 1];
        int in;
        for (int i = 0;i < n + 1;i++){
            for (int j = i + 1;j < n + 1;j++){
                in = sc.nextInt();
                eds[i][j] = new Edge();
                eds[i][j].first = i;
                eds[i][j].next = j;
                eds[i][j].weight = in;
                eds[j][i] = new Edge();
                eds[j][i].first = j;
                eds[j][i].next = i;
                eds[j][i].weight = in;
                if (in < min.weight)
                    min = eds[i][j];
            }
        }
        prim(point,eds);
        out.println(sum);

        out.close();
    }

    public static void prim(int[] point, Edge[][] eds){
        count = 0;
        Queue<Edge> queue = new PriorityQueue<>(
                Comparator.comparingInt(o -> o.weight));
        point[min.first] = -1;
        point[min.next] = -1;
        for (int i = 0;i < eds.length;i++){
            if (i != min.first) {
                queue.add(eds[min.first][i]);
            }
            if (i != min.next) {
                queue.add(eds[min.next][i]);
            }
        }
        sum += min.weight;
        count += 1;
        while (!queue.isEmpty()){
            ee = queue.poll();

            if (count == eds.length - 1)
                break;
            if (point[ee.next] == -1)
                continue;

            sum += ee.weight;
            count += 1;
            point[ee.next] = -1;
            for (int i = 0;i < eds.length;i++){
                if (ee.next == i)
                    continue;
                if (point[eds[ee.next][i].next] != -1){
                    queue.add(eds[ee.next][i]);
                }
            }

        }

    }
    
    static class Edge{
        int weight = max;
        int first = -1;
        int next = -1;

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
