package lab11;

import java.io.*;
import java.util.*;

public class VCNsString {

    static long[][] table = new long[61][3];
    static long v,c,n;
    public static void main(String[] args) throws IOException {
        Reader sc = new Reader();
        PrintWriter out=new PrintWriter(System.out);
        table[0][0] = 1;
        for (int i = 1;i <= 60;i++){
            table[i][0] = table[i-1][0] + table[i-1][2];
            table[i][1] = table[i-1][0] + table[i-1][1];
            table[i][2] = table[i-1][1] + table[i-1][2];
        }

        int size = sc.nextInt();
        while (size-->0){
            v = 0;
            c = 0;
            n = 0;
            long r = sc.nextLong();
            count(r,0);
            System.out.println(v + " " + c + " " + n);
        }

        out.close();
    }


    static void count(long r, int switches){
        if (r == 0)
            return;
        if (r == 1){
            switch (switches){
                case 0:
                    v += 1;
                    return;
                case 1:
                    c += 1;
                    return;
                case 2:
                    n += 1;
                    return;
            }
        }
//        if (r == 2){
//            switch(switches){
//                case 0:
//                    v += 1;
//                    return;
//                case 1:
//                    c += 1;
//                    return;
//                case 2:
//                    n += 1;
//                    return;
//            }
//        }
        int index = (int)log2(r);
        switch (switches){
            case 0:
                v += table[index][0];
                c += table[index][1];
                n += table[index][2];
                break;
            case 1:
                v += table[index][2];
                c += table[index][0];
                n += table[index][1];
                break;
            case 2:
                v += table[index][1];
                c += table[index][2];
                n += table[index][0];
                break;
        }

        count(r - pow2(index),(switches + 1) % 3);
    }


    static long log2(long tt){
        long re = 0;
        while (tt > 1) {
            tt = tt / 2;
            re ++;
        }
        return re;
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
