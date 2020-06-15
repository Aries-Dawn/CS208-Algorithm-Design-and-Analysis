package lab8;


import java.io.*;
import java.math.*;
import java.util.*;

public class MalaysianTranslator {

    static Node root;
    static PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.num - o2.num;
        }
    });

    public static void main(String[] args) {
        InputStream inputStream = System.in;// new FileInputStream("C:\\Users\\wavator\\Downloads\\test.in");
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        solver.solve(in, out);
        out.close();
    }

    static class Task {

        public void solve(InputReader sc, PrintWriter out) {
            int size = sc.nextInt();
            String temp;
            Character c;
            long length = 0;
            ArrayList<Character> ch = new ArrayList<>();
            HashMap<Character,Node> map = new HashMap<>();
            while (size-- > 0){
                length = 0;
                pq.clear();
                map.clear();
                ch.clear();
                root = new Node(' ',-1,0);
                temp = sc.next();
                for (int i = 0;i < temp.length();i++){
                    c = temp.charAt(i);
                    if (map.containsKey(c))
                        map.get(c).num++;
                    else {
                        map.put(c, new Node(c, 1, 0));
                        ch.add(c);
                    }
                }
                for (int i = 0;i < ch.size();i++){
                    pq.add(map.get(ch.get(i)));

                }
                HuffmanTree();
//                System.out.println();
                Node temps;
                for (int i = 0;i < ch.size();i++){
                    temps = map.get(ch.get(i));
                    length += temps.height * temps.num;
                }
                out.println(length);

            }

        }

        private static void HuffmanTree(){
            Node temp;
            Node temp2;
            if (pq.size() == 1){
                temp = pq.poll();
                root.left = temp;
                temp.height = root.height + 1;
            }
            else if (pq.size() == 2){
                temp = pq.poll();
                temp2 = pq.poll();
                root.left = temp;
                temp.height = root.height + 1;
                root.right = temp2;
                temp2.height = root.height + 1;
            }
            else {
                temp = pq.poll();
                temp2 = pq.poll();
                Node tt = new Node(' ',temp.num + temp2.num,0);
                pq.add(tt);
                HuffmanTree();
                tt.left = temp;
                temp.height = tt.height + 1;
                tt.right = temp2;
                temp2.height = tt.height + 1;
            }
        }
    }

    static class Node {
        char character;
        int num;
        int height;
        Node parent;
        Node left;
        Node right;

        public Node(){}
        public Node(char character, int num, int height){
            this.character = character;
            this.height = height;
            this.num = num;
        }
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public char[] nextCharArray() {
            return next().toCharArray();
        }

        //         public boolean hasNext() {
//             try {
//                 return reader.ready();
//             } catch(IOException e) {
//                 throw new RuntimeException(e);
//             }
//         }
        public boolean hasNext() {
            try {
                String string = reader.readLine();
                if (string == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(string);
                return tokenizer.hasMoreTokens();
            } catch (IOException e) {
                return false;
            }
        }

        public BigInteger nextBigInteger() {
            return new BigInteger(next());
        }

        public BigDecimal nextBigDecinal() {
            return new BigDecimal(next());
        }
    }
}
