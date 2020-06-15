package lab3;
//快读模板2：较慢，但有next()

import java.io.*;
import java.math.*;
import java.util.*;

public class P2_fast {

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


            long sum = 0;
            int n = sc.nextInt();
            int[] length = new int[11];
            String[] data = new String[n];
            for (int i = 0;i < n;i++){
                String temp = sc.next();
                data[i] = temp;
                length[temp.length()]++;
            }
            for (int i = 0;i < n;i++){
                //find smaller item
                int len = data[i].length();
                for (int j = len - 1;j > 0;j--){
                    if (length[j] != 0){
                        long pre = 0;
                        int sub = len - j;
                        for (int k = 0;k < sub;k++){
                            pre += (long)data[i].charAt(k) - '0';
                            pre %= 998244353;
                            pre *= (long) 10;
                        }
                        pre /= 10;

                        long temp = pre;
                        //as a
                        for (int k = sub;k < len;k++){
                            pre *= (long) 10;
                            pre += (long)data[i].charAt(k) - '0';
                            pre %= 998244353;
                            pre *= (long) 10;
                        }
                        sum += ((long)length[j] * (pre % 998244353)) % 998244353;
                        sum %= 998244353;

                        pre = temp;
                        //as b
                        for (int k = sub;k < len;k++){
                            pre *= (long) 100;
                            pre += (long)data[i].charAt(k) - '0';
                            pre %= 998244353;
                        }
                        sum += ((long)length[j] * (pre % 998244353)) % 998244353;
                        sum %= 998244353;

                    }
                }
                //find larger item
                int allBiggerNum = 0;
                for (int j = len;j < 11;j++){
                    allBiggerNum += length[j];
                }
                long pre = 0;
                //as a
                pre += (long)data[i].charAt(0) - '0';
                pre %= 998244353;
                pre *= (long) 10;
                for (int k = 1;k < len;k++){
                    pre *= (long) 10;
                    pre += (long)data[i].charAt(k) - '0';
                    pre %= 998244353;
                    pre *= (long) 10;
                }
                sum += ((long)allBiggerNum * (pre % 998244353)) % 998244353;
                sum %= 998244353;

                //as b
                pre /= 10;

                sum += ((long)allBiggerNum * (pre % 998244353)) % 998244353;
                sum %= 998244353;
            }

            System.out.println(sum % 998244353);




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