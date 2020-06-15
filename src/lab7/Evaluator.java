package lab7;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

@SuppressWarnings("all")
public class Evaluator {
    private static int count = 0, expect = 1000;
    private static final PrintStream OUT = System.out;
    private static ByteArrayInputStream in;
    private static ByteArrayOutputStream bs;
    private static PrintStream ps;
    private static long[] times = new long[2];
    private static String[] res = new String[2];
    private static StringBuilder input;
    public static void main(String[] args) throws Exception {
        while(expect-->0) {
            generateInputData();
            try {
                runJava(VinceandYee.class, input, 0);

                runCpp("lab7-2.exe", input, 1);

                System.setOut(OUT);
                System.gc();
                if (isSuccess()) {
                    onSuccess(input);
                } else {
                    onWrong(input);
                }
            } catch (Exception e) {
                onErr(input, e);
            }
        }
    }

    /**
     * Execute when RE
     * @param input
     * @param e
     * @throws Exception
     */
    private static void onErr(StringBuilder input, Exception e) throws Exception{
        e.printStackTrace();
        PrintWriter pw = new PrintWriter("test.txt");
        pw.println(input.toString());
        pw.println(e.toString());
        pw.close();
    }

    /**
     * Execute when WA
     * @param input
     * @throws Exception
     */
    private static void onWrong(StringBuilder input) throws Exception{
        System.err.println("Wrong, read the test.txt");
        PrintWriter pw = new PrintWriter("test.txt");
        pw.println(input.toString());
        pw.println("Result0: "+res[0] + " Result1: " + res[1]);
        pw.close();
    }

    private static void onSuccess(StringBuilder input) throws Exception {
        System.out.println("Success! Count:" + count++ + " Time0： " + times[0] + " Time1: " + times[1]);
        System.out.println("Result: "+res[0]);
    }

    private static void runCpp(String exe,StringBuilder input,int index) throws Exception {
        Runtime rt = Runtime.getRuntime();
        Process pB = rt.exec(exe);
        pB.getOutputStream().write(input.toString().getBytes());
        pB.getOutputStream().flush();
        long start = System.currentTimeMillis();
        pB.waitFor();
        long stop = System.currentTimeMillis();
        byte[] bytes = new byte[1000];
        pB.getInputStream().read(bytes);
        res[index] = bs.toString().trim();
        times[index] = stop - start;
    }

    /**
     * Java runner
     * @param task
     * @throws IOException
     */
    private static void runJava(Class task,StringBuilder input,int index) throws Exception {
        redirect(input);
        long start = System.currentTimeMillis();
        task.getMethod("main",String[].class).invoke(null,(Object)new String[]{});
        long stop = System.currentTimeMillis();
        res[index] = bs.toString().trim();
        times[index] = stop - start;
    }


    /**
     * Input generator
     *
     * @return
     */
    private static void generateInputData() {
        Random r = new Random();
        int n = 2000;
        input = new StringBuilder();
        input.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                input.append(r.nextInt(Integer.MAX_VALUE) + 1).append(" ");
            }
            input.append("\n");
        }
    }

    /**
     * Java Input/Output redirect
     *
     * @param sb
     */
    private static void redirect(StringBuilder sb) {
        in = new ByteArrayInputStream(sb.toString().getBytes());
        System.setIn(in);
        bs = new ByteArrayOutputStream();
        ps = new PrintStream(bs);
        System.setOut(ps);
    }

    /**
     * Judge whether the two results are seccuss to pass
     * @return
     */
    private static boolean isSuccess(){
        return res[0].equals(res[1]);
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
