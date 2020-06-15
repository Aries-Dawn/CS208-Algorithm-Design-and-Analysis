package lab9;
//快读模板1：更快，但没有next()用于读字符串

import java.io.*;
import java.util.*;

public class VC_String {

    static boolean in = false;
    static final double loge2 = Math.log(2);
    static long R_re,L_re;
    static long old;
    public static void main(String[] args) throws IOException {
        Reader sc=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        int n = sc.nextInt();
        while (n-->0){
            long L = sc.nextLong();
            long R = sc.nextLong();
            in = false;
            old = -1;
            R_re = de(R);
            in = false;
            old = -1;
            L_re = de(L-1);
            System.out.println(R_re - L_re);
        }
        out.close();
    }

    public static long de(long x){
        if (x == 0)
            return 0;

        long a = log2(x);
        long bound = pow2(a)-1;
        long sum = pow2(a-1);
        if (old == -1 || old - a != 1)
            in = false;
        old = a;
//        System.out.println(a + " "+  sum);
        if (x == bound)
            return sum;
        else {
            if (!in) {
                sum += 1;
                in = true;
            }
            sum += de(x - bound - 1);
            return sum;
        }
    }

    static long log2(long x){
        double a = Math.log(x+1)/loge2;
        return (long)a;
    }

    static long pow2(long index){
        long re = 1;
        for (long i = 0;i < index;i++){
            re *= 2;
        }
        return re;
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
