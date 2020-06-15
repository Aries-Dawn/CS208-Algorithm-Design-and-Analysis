package lab13;

//快读模板1：更快，但没有next()用于读字符串

import java.io.*;
import java.util.*;

public class Wellplayed {

    public static void main(String[] args) throws IOException {
        Reader sc = new Reader();
        PrintWriter out = new PrintWriter(System.out);

        int n = sc.nextInt();
        int k = sc.nextInt();
        int c = sc.nextInt();
        int cost, attack, hp, value;


        Card[][] in = new Card[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= k; j++) {
                in[i][j] = new Card(0, 0);
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                cost = sc.nextInt();
                attack = sc.nextInt();
                hp = sc.nextInt();
                value = attack + hp;
                in[i][j].cost = cost;
                in[i][j].value = value;
            }
        }

        int[][] dp = new int[n + 1][c + 1];

        //TODO: OPT(i,w) =max(OPT(i-1, w), 𝑣_𝑘1  + OPT(i-1, w-𝑤_𝑘1), 𝑣_𝑘2  + OPT(i-1, w-𝑤_𝑘2), 𝑣_𝑘3  + OPT(i-1, w-𝑤_𝑘3))   while  k =3

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= c; w++) {
                dp[i][w] = dp[i - 1][w];
                for (int j = 1; j <= k; j++) {
                    if (w - in[i][j].cost >= 0)
                        dp[i][w] = Math.max(dp[i][w], in[i][j].value + dp[i - 1][w - in[i][j].cost]);
                }
            }
        }
        System.out.println(dp[n][c]);

        out.close();
    }

    static class Card {
        int cost;
        int value;

        Card(int cost, int value) {
            this.cost = cost;
            this.value = value;
        }
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
