package lab6;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SuppressWarnings("all")
public class Evaluator {
    static int count = 0;

    public static void main(String[] args) throws IOException, InterruptedException {
        Random r = new Random();
//        int n = r.nextInt(10000)+1;
//        int m = r.nextInt(100000)+1;
        while (true){
            int n = 1000, m=10000;
            StringBuilder sb = new StringBuilder();
            sb.append(n).append("\n").append(m).append("\n");
            for(int i=0;i<m;i++){
                sb.append(r.nextInt(n)+1).append(" ").append(r.nextInt(n)+1)
//            sb.append(i+1).append(" ").append(i+2)
//                    .append(" ").append(100000).append("\n");
                        .append(" ").append(r.nextInt(100000)).append("\n");
            }
            sb.append(0).append(" ").append(r.nextInt(100000)).append("\n");
            for (int i = 1; i < n-1; i++) {
                int a = r.nextInt(100000);
                int b = r.nextInt(100000);
//            int a = 100000,b=100000;
                if(b==0)
                    b++;
                sb.append(a).append(" ").append(b).append("\n");
            }
            sb.append(0).append(" ").append(r.nextInt(100000)).append("\n");
            PrintWriter pw0 = new PrintWriter("test.txt");
            pw0.println(sb.toString());
            pw0.close();
            ByteArrayInputStream in = new ByteArrayInputStream(sb.toString().getBytes());
            System.setIn(in);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(bs);
            final PrintStream OUT = System.out;
            System.setOut(ps);

            long start = System.currentTimeMillis();
            Lab6_2.main(null);
            long stop = System.currentTimeMillis();
            String re0 = bs.toString().trim();
            long myTime = stop - start;
            System.setOut(OUT);

            Runtime rt = Runtime.getRuntime();
            in = new ByteArrayInputStream(sb.toString().getBytes());
            System.setIn(in);
            bs = new ByteArrayOutputStream();
            ps = new PrintStream(bs);
            System.setOut(ps);
            Process pB = rt.exec("lab6-2.exe");
            pB.getOutputStream().write(sb.toString().getBytes());
            pB.getOutputStream().flush();
            start = System.currentTimeMillis();
            byte[] bytes = new byte[1000];
            pB.waitFor();
            stop = System.currentTimeMillis();
            pB.getInputStream().read(bytes);
            String re1 = new String(bytes).trim();
            long cyfTime = stop - start;
            System.setOut(OUT);
            if (re0.equals(re1)||re0.equals("-1")&&re1.equals("4557430888798830399")) {
                System.out.println(true + " " + count++ + " myTime： " + myTime + " cyfTime: " + cyfTime);
                System.out.println(re0 + " " + re1);
//                Evaluator.main(null);
                continue;
            } else {
                PrintWriter pw = new PrintWriter("test.txt");
                pw.println(sb.toString());
                pw.println(re0 + " " + re1);
                pw.close();
            }
            System.setOut(OUT);
            System.out.println(re0+" "+myTime);
            break;
        }
    }


    private static void shuffle(int n, String[] boys, String[] girls, StringBuilder sb) {
        for (int i = 0; i < n; i++) {
            sb.append(girls[i] + " ");
            List<String> b = Arrays.asList(boys.clone());
            Collections.shuffle(b);
            for (int j = 0; j < n; j++) {
                sb.append(b.get(j) + " ");
            }
            sb.append("\n");
        }
    }
}
