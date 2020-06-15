package lab5;

import java.io.*;

public class fast5_1 {

    static int lo1,lo2,hi1,hi2;
    public static void main(String[] args) throws IOException {
        Reader sc = new Reader();
        PrintWriter out=new PrintWriter(System.out);

        int size = sc.nextInt();
        while (size-- > 0){
            int count = 0;
            int lastEndTime = 0;
            int num = sc.nextInt();
            int[][] data = new int[num][2];
            for (int i = 0;i < num;i++){
                data[i][0] = sc.nextInt();
                data[i][1] = sc.nextInt();
            }
            quickSort(data,0,data.length - 1);

            for (int[] datum : data) {
                if (datum[0] > lastEndTime) {
                    count++;
                    lastEndTime = datum[1];
                }
            }
            System.out.println(count);

        }


        out.close();
    }


    public static void quickSort(int[][] num, int low, int high){
        if (low < high){
            int p = position(num,low,high);
            quickSort(num,low,p - 1);
            quickSort(num,p + 1,high);
        }
    }

    private static int position(int[][] num, int low, int high){
        int positions = low;
        int positionNum = num[low][1];
        while (low < high){
            while (low < high && num[high][1] >= positionNum)
                high--;
            while (low < high && num[low][1] <= positionNum)
                low++;
            swap(num,low,high);
        }
        swap(num,positions,low);
        return low;
    }

    private static void swap(int[][] num, int low, int high){
        lo1 = num[low][0];
        lo2 = num[low][1];
        hi1 = num[high][0];
        hi2 = num[high][1];
        num[low][0] = hi1;
        num[low][1] = hi2;
        num[high][0] = lo1;
        num[high][1] = lo2;
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

        public int nextint() throws IOException
        {
            int ret = 0;
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
