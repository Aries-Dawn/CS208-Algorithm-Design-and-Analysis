package lab3;


import java.io.*;
import java.math.*;
import java.util.*;

public class P1_fast {

    public static void main(String[] args) {
        InputStream inputStream = System.in;// new FileInputStream("C:\\Users\\wavator\\Downloads\\test.in");
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        solver.solve(in, out);
        out.close();
    }
    private static class Node{
        int value;
        int index;
        Node pre;
        Node next;
    }
    static class Task {

        public void solve(InputReader sc, PrintWriter out) {

            
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
            System.gc();
            Node head = new Node();
            head.value = -1;
            Node tail = new Node();
            tail.value = -1;
            head.next = tail;
            tail.pre = head;
            int index = 0;
            for (Node temp : bucket){
                if (temp != null){
                    temp.pre = tail.pre;
                    tail.pre.next = temp;
                    tail.pre = temp;
                    temp.next = tail;
                    temp.index = index;
                    index++;
                }

            }

//            System.gc();
            int sum = 0;
            for (int i = N - 1;i > 0;i--){
                if (data[i].pre != null & data[i].next != null) {
                    int pre = Math.abs(data[i].value - data[i].pre.value);
                    int next = Math.abs(data[i].value - data[i].next.value);
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