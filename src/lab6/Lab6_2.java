package lab6;

import java.io.*;
import java.util.*;

public class Lab6_2 {
    static long[] distance;

    public static void main(String[] args) throws IOException {
        Reader sc = new Reader();
        int N = sc.nextInt();
        ArrayList<HashMap<Integer,Integer>> adjacentTable = new ArrayList<>(N + 1);

        int M = sc.nextInt();
        distance = new long[N + 1];
        byte[] S = new byte[N + 1];
        int[] a = new int[N + 1];
        int[] b = new int[N + 1];

        Arrays.fill(distance,Long.MAX_VALUE);
        distance[0] = 0;

        for (int i = 0; i < N + 1; i++) {
            adjacentTable.add(new HashMap<>());
        }

        for (int i = 0; i < M; i++) {
            adjacentTable.get(sc.nextInt()).put(sc.nextInt(),sc.nextInt());
        }
        for (int i = 1; i < N + 1; i++) {
            a[i] = sc.nextInt();
            b[i] = sc.nextInt();
        }

        PriorityQueue<Integer> Q = new PriorityQueue<>(Comparator.comparingLong(o -> distance[o]));
        Q.add(1);
        distance[1] = 0;

        while (!Q.isEmpty()) {
            int A = Q.poll();
            S[A] = 1;
//            if (A == N) {
//                break;
//            }
            for (Integer B : adjacentTable.get(A).keySet()) {
                int w = adjacentTable.get(A).get(B);
                long actural_time = distance[A] + w;
                long diff = (distance[A] + w) % (a[B] + b[B]);
                if (diff < a[B]) {
                    actural_time += a[B] - diff;
                }
                if (S[B] == 0 && actural_time < distance[B]) {
                    distance[B] = actural_time;
                    Q.add(B);
                }
//                if (S[B] == 0 && distance[A] + w < distance[B]) {
//                    distance[B] = distance[A] + w;
//                    Q.add(B);
//                }
            }
        }
        System.out.println(distance[N]);
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

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            boolean neg = (c == '-');
            if (neg) {
                c = read();
            }
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg) {
                return -ret;
            }
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1) {
                buffer[0] = -1;
            }
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead) {
                fillBuffer();
            }
            return buffer[bufferPointer++];
        }

    }
}
