package lab5;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Evaluator {
    static int count = 0;

    public static void main(String[] args) throws IOException, InterruptedException {
        Random r = new Random();
        while (true){
            int n = r.nextInt(100)+1;

            StringBuilder sb = new StringBuilder();
            sb.append(n + "\n");
            for (int i = 0; i < n; i++) {
                int start = r.nextInt(10000) + 1;
                sb.append(start).append(" ")
                        .append((start + r.nextInt(10000 - start + 1))).append("\n");
            }
            sb.append("\n");
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
            Lanran_Second_Girlfriend.main(null);
            long stop = System.currentTimeMillis();
            String re0 = bs.toString().trim();
            long myTime = stop - start;
//        System.setOut(OUT);

            Runtime rt = Runtime.getRuntime();
//        in = new ByteArrayInputStream(sb.toString().getBytes());
//        System.setIn(in);
//        bs = new ByteArrayOutputStream();
//        ps = new PrintStream(bs);
//        System.setOut(ps);
            start = System.currentTimeMillis();
//        Lslnb.main(null);//TODO 你的main
            Process pB = rt.exec("lab5-2.exe");
            pB.getOutputStream().write(sb.toString().getBytes());
            pB.getOutputStream().flush();
            byte[] bytes = new byte[1000];
            pB.waitFor();
            pB.getInputStream().read(bytes);
            stop = System.currentTimeMillis();
            String re1 = new String(bs.toByteArray()).trim();
//        datatest.main(null);
            long lslTime = stop - start;
            System.setOut(OUT);
            if (re0.equals(re1)) {
                System.out.println(true + " " + count++ + " myTime： " + myTime + " OtherTime: " + lslTime);
                System.out.println(re0 + " " + re1);
//                Evaluator.main(null);
            } else {
                PrintWriter pw = new PrintWriter("test.txt");
                pw.println(sb.toString());
                pw.println(re0 + " " + re1);
                pw.close();
                break;
            }
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
