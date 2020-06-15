package lab14;

//快读模板1：更快，但没有next()用于读字符串

import java.io.*;
import java.util.*;

public class Vince_BlackAndVince_White {

    static ArrayList<ArrayList<Node>> edge;
    static Node[] node;
    static int n, m;
    static int s, t;

    public static void main(String[] args) throws IOException {
        Reader sc = new Reader();
        PrintWriter out = new PrintWriter(System.out);

        n = sc.nextInt();
        m = sc.nextInt();
        int a, b, color;
        node = new Node[n + 2];
        edge = new ArrayList<>();
        for (int i = 0; i < node.length; i++) {
            edge.add(new ArrayList<>());
        }
        s = 0;
        t = n + 1;
        node[s] = new Node(0, s);
        node[t] = new Node(0, t);
        for (int i = 1; i < n + 1; i++) {
            color = sc.nextInt();
            node[i] = new Node(color, i);
            if (color == 1)
                edge.get(s).add(node[i]);
            else
                edge.get(i).add(node[t]);
        }

        for (int i = 0; i < m; i++) {
            a = sc.nextInt();
            b = sc.nextInt();
            edge.get(a).add(node[b]);
            edge.get(b).add(node[a]);
        }
        int re = 0;

        re = dinic();

        out.println(re);

        out.close();
    }

    static Node temp;

    static int dinic(){
        int re = 0;
        while (bfs()) {
            while (dfs(node[0])) {
                re += 1;
            }
        }
        return re;
    }

    static boolean bfs() {
        for (int i = 0; i < n + 2; i++) {
            node[i].rank = -1;
        }
        boolean result = false;
        Queue<Node> queue = new LinkedList<>();
        queue.add(node[s]);
        node[s].rank = 0;
        while (!queue.isEmpty()) {
            temp = queue.poll();
            if (temp.index == t)
                result = true;
            for (int i = 0; i < edge.get(temp.index).size(); i++) {
                if (edge.get(temp.index).get(i).rank == -1) {
                    edge.get(temp.index).get(i).rank = temp.rank + 1;
                    queue.add(edge.get(temp.index).get(i));
                }
            }
        }

        return result;
    }


    static boolean dfs(Node tt) {
        for (int i = 0; i < edge.get(tt.index).size(); i++) {
            if (edge.get(tt.index).get(i).index == t) {
                edge.get(tt.index).remove(node[t]);
                return true;
            }
            if (edge.get(tt.index).get(i).rank == tt.rank + 1) {
                if (dfs(edge.get(tt.index).get(i))) {
                    edge.get(tt.index).remove(i);
                    return true;
                }
            }
        }
        return false;
    }


//    static Stack<Node> stack = new Stack<>();
//    static boolean dfs(){
//        stack.clear();
//        stack.push(node[s]);
//        Node temp;
//        boolean pushed = false;
//        boolean result = false;
//        while (!stack.isEmpty()){
//            pushed = false;
//            temp = stack.peek();
//            for (int i = 0;i < edge.get(temp.index).size();i++){
//                if (edge.get(temp.index).get(i).index == t){
//                    result = true;
//                    while (!stack.isEmpty()){
//                        temp = stack.pop();
//                        edge.get(temp.index).remove(temp.next)
//                    }
//                    break;
//                }
//                if (edge.get(temp.index).get(i).rank == temp.rank+1){
//                    pushed = true;
//                    temp.next = edge.get(temp.index).get(i);
//                    queue.add(new int[]{temp.index,i});
//                    stack.push(edge.get(temp.index).get(i));
//                    break;
//                }
//            }
//            if (result)
//                return true;
//            if (!pushed)
//                stack.pop();
//        }
//        return result;
//    }


    static class Node {
        int index;
        int color;
        int rank;
        Node next;
        int used;

        Node(int color, int index) {
            this.color = color;
            this.index = index;
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
