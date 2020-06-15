package lab9;

import java.io.*;
import java.util.*;

public class ExamsAreComing {

    static long sum = 0;
    static long res;
    static int index = 0;

    public static void main(String[] args) throws IOException {
        Reader sc=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        long size = sc.nextLong();
        if (size == 0)
            return;
        Node[] all = new Node[(int)size];
        for (int i = 0;i < size;i++){
            all[i] = new Node(sc.nextLong(),sc.nextLong());
        }
        Arrays.sort(all, (o1, o2) -> {
            if (o1.A == o2.A)
                return (int) (o1.B - o2.B);
            else
                return (int) (o1.A - o2.A);
        });
        sum = size*(size - 1)/2;
        sum -= sortAndCount(all).r;
        System.out.println(sum);
        out.close();
    }

    static re sortAndCount(Node[] L){
        if (L.length == 1)
            return new re(0,L);
        Node[] A = new Node[L.length / 2];
        Node[] B = new Node[L.length - A.length];
        for (int i = 0;i < A.length;i++){
            A[i] = L[i];
        }
        for (int i =A.length;i < L.length;i++){
            B[i - A.length] = L[i];
        }
        re reA = sortAndCount(A);
        A = reA.res;
        re reB = sortAndCount(B);
        B = reB.res;
        re reL = MergeAndCount(A,B);

        return new re(reA.r+reB.r+reL.r,reL.res);
    }

    static re MergeAndCount(Node[] A,Node[] B){
        Node[] L = new Node[A.length + B.length];
        res = 0;
        index = 0;
        int i = 0,j = 0;
        while (i < A.length && j < B.length){
            if (A[i].B <= B[j].B){
                L[index] = A[i];
                i++;
                index++;
            }
            else {
                L[index] = B[j];
                j++;
                index++;
                res += A.length - i;
            }
        }
        while (i < A.length){
            L[index] = A[i];
            i++;
            index++;
        }

        while (j < B.length){
            L[index] = B[j];
            j++;
            index++;
        }

        return new re(res,L);
    }

    static class re{
        long r;
        Node[] res;

        public re(long r, Node[] res) {
            this.r = r;
            this.res = res;
        }
    }

    static class Node{
        long A;
        long B;

        Node(long A,long B){
            this.A = A;
            this.B = B;
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
