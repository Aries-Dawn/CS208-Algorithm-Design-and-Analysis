package lab14;

import java.io.*;
import java.util.*;

public class BallBallVince {

    public static void main(String[] args) throws IOException {
        Reader sc = new Reader();
        PrintWriter out = new PrintWriter(System.out);

        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2-o1);
        int n = sc.nextInt();

        int[] A = new int[n+1];
        int[] B = new int[n+1];
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            A[i] = sc.nextInt();
            sum += A[i];
            B[i] = sc.nextInt();
            pq.add(B[i]);
        }
        int num = 0;
        int s = sum;

        while (sum > 0) {
            sum -= pq.poll();
            num++;
        }


        int[][][] dp = new int[2][n + 1][100 * num - s + 1];

        for (int k = 1; k < num + 1; k++) {
            for (int i = 0;i < n+1;i++){
                for (int j = 0;j < 100 * num - s + 1;j++){
                    dp[k%2][i][j] = -1;
                }
            }
            for (int i = 1; i < n + 1; i++) {
                for (int j = 0; j < 100 * num - s + 1; j++) {
                    if (j >= (100 - B[i]) && dp[(k-1)%2][i - 1][j - (100 - B[i])] >=0) {
                        dp[k%2][i][j] =
                                Math.max(dp[k%2][i - 1][j], dp[(k-1)%2][i - 1][j - (100 - B[i])] + A[i]);
                    } else {
                        dp[k%2][i][j] = dp[k%2][i - 1][j];
                    }
                }
            }
        }

        out.println(s - dp[num%2][n][100 * num - s]);


        out.close();
    }


    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
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

        public double nextDouble() throws IOException {
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

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }

}

