package lab8;

import java.io.*;
import java.util.*;

public class Q9_2 {
    static class Task {
        int S;
        int T;
        int V;

        Task(int S, int T, int V) {
            this.S = S;
            this.T = T;
            this.V = V;
        }
    }
    static HashMap<Long,Integer> map = new HashMap<>();
    static Task[] tasks;
    public static void main(String[] args) throws IOException {
        map = new HashMap<>();
        Sc sc = new Sc();
        int n = sc.nI();
        tasks = new Task[n];
        for (int i = 0; i < n; i++) {
            tasks[i] = new Task(sc.nI(), sc.nI(), sc.nI());
        }
        Arrays.sort(tasks, (o1, o2) -> o2.V - o1.V);
        int tmp = 0;
        for (int cnt = 0; cnt < tasks.length; cnt++) {
            tmp = check(cnt, tasks[cnt].S);
        }
        Set<Long> keySet = map.keySet();
        long answer = tmp;
        for (Long o : keySet) {
            answer = answer + tasks[map.get(o)].V;
        }
        answer -= tmp;
        System.out.println(answer);
    }
    static int check(int counter, long index) {
        if (index > tasks[counter].T) {
            return 0;
        }
        if (!map.containsKey(index)) {
            map.put(index, counter);
            return 1;
        } else {
            if (tasks[counter].T > tasks[map.get(index)].T) {
                return check(counter, index + 1);
            } else {
                if (check(map.get(index), index + 1) == 1) {
                    map.put(index, counter);
                    return 1;
                }
            }
            return 0;
        }
    }

    static class Sc {
        final private int BUFFER_SIZE = 1 << 16;
        private final DataInputStream inStream;
        private final byte[] buff;
        private int pnt, bRe;

        public Sc() {
            inStream = new DataInputStream(System.in);
            buff = new byte[BUFFER_SIZE];
            pnt = bRe = 0;
        }


        public int nI() throws IOException {
            int res = 0;
            byte c = getIn();
            while (c <= ' ') c = getIn();
            boolean neg = (c == '-');
            if (neg) c = getIn();
            do {
                res = res * 10 + c - '0';
            } while ((c = getIn()) >= '0' && c <= '9');

            if (neg) return -res;
            return res;
        }


        private void fillBuff() throws IOException {
            bRe = inStream.read(buff, pnt = 0, BUFFER_SIZE);
            if (bRe == -1) buff[0] = -1;
        }

        private byte getIn() throws IOException {
            if (pnt == bRe) fillBuff();
            return buff[pnt++];
        }
    }
}
