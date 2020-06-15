package lab13;

//快读模板1：更快，但没有next()用于读字符串

import java.io.*;
import java.util.*;

public class SleepyVince {


    static int mod = 998244353;

    public static void main(String[] args) throws IOException {
        Reader sc = new Reader();
        PrintWriter out = new PrintWriter(System.out);

        int n = sc.nextInt();
        int[] data = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            data[i] = sc.nextInt();
        }

        int[][][] dp = new int[201][2][n + 1];

        if (data[1] == -1) {
            for (int j = 1; j <= 200; j++) {
                dp[j][0][1] = j;
            }
        } else {
            dp[data[1]][0][1] = 1;
            for (int i = data[1] + 1; i <= 200; i++) {
                dp[i][0][1] = dp[i - 1][0][1];
            }
        }
        for (int i = 2; i <= n; i++) {
            if (data[i] == -1) {
                for (int j = 1; j <= 200; j++) {
                    dp[j][0][i] = ((dp[j - 1][0][i] + dp[j - 1][1][i - 1]) % mod + dp[j - 1][0][i - 1]) % mod;
                    dp[j][1][i] = ((((dp[j - 1][1][i] + dp[200][1][i - 1]) % mod - dp[j - 1][1][i - 1]) % mod + dp[j][0][i - 1]) % mod - dp[j - 1][0][i - 1]) % mod;
                }
            } else {
                dp[data[i]][0][i] = ((dp[data[i] - 1][0][i] + dp[data[i] - 1][1][i - 1]) % mod + dp[data[i] - 1][0][i - 1]) % mod;
                dp[data[i]][1][i] = ((((dp[data[i] - 1][1][i] + dp[200][1][i - 1]) % mod - dp[data[i] - 1][1][i - 1])
                        % mod + dp[data[i]][0][i - 1]) % mod - dp[data[i] - 1][0][i - 1]) % mod;
                for (int j = data[i] + 1; j <= 200; j++) {
                    dp[j][0][i] = dp[j-1][0][i];
                    dp[j][1][i] = dp[j-1][1][i];
                }
            }
        }

        System.out.println((dp[200][1][n] + mod)%mod);

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

