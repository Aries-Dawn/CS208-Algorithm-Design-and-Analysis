package lab3;
//快读模板1：更快，但没有next()用于读字符串

import java.io.*;

public class P1_fast1 {

    private static class Node{
        int value;
        Node pre;
        Node next;
    }
    
    public static void main(String[] args) throws IOException {
        Reader sc=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        
        
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
//        System.gc();
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



        out.close();
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
