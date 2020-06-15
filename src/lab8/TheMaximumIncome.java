package lab8;

import java.io.*;
import java.util.*;

public class TheMaximumIncome {

    static PriorityQueue<work> pq = new PriorityQueue<>((o1, o2) -> o2.v - o1.v);
    static work[] all;
    static int[] active;
    static int[] time_slot;

    public static void main(String[] args) throws IOException {
        Reader sc=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int n = sc.nextInt();
        int s,t,v;
        int[] ss = new int[n];
        time_slot = new int[100000000];
        active = new int[n];
        all = new work[n];
        for (int i = 0;i < n;i++){
            s = sc.nextInt();
            t = sc.nextInt();
            v = sc.nextInt();
            pq.add(new work(s,t,v));
            ss[i] = s;
        }
        Arrays.sort(ss);
        int x = 0;
        for (int i = 0;i < n;i++){
            x = max(x + 1,ss[i]);
            active[i] = x;
        }
        for (int i = 0;i < n;i++){
            all[i] = pq.poll();
        }
        for (int i = 0;i < n;i++){
            x = all[i].s;
            find(i,x);
        }
        int count = 0;
        long sum = 0;
        for (int i = 0;i < time_slot.length;i++){
            if (time_slot[i] != 0){
                sum += all[time_slot[i] - 1].v;
                count++;
            }
            if (count == n)
                break;
        }
        System.out.println(sum);
        out.close();
    }

    static int j;
    static boolean find(int i,int x){
        if (x > all[i].t)
            return false;
        if (time_slot[x] == 0) {
            time_slot[x] = i + 1;
            return true;
        }
        j = time_slot[x] - 1;
        if (all[i].t > all[j].t)
            return find(i,x + 1);
        else {
            if (find(j,x + 1)){
                time_slot[x] = i + 1;
                return true;
            }
            else
                return false;
        }

    }

    static int max(int a,int b){
        if (a >= b)
            return a;
        else
            return b;
    }
    static class work{
        int s;
        int t;
        int v;

        work(int s,int t,int v){
            this.s = s;
            this.t = t;
            this.v = v;
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
