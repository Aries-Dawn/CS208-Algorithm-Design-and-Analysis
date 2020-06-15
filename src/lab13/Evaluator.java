package lab13;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SuppressWarnings("all")
public class Evaluator {
    private static int count = 0, expect = 100000;
    private static final PrintStream OUT = System.out;
    private static ByteArrayInputStream in;
    private static ByteArrayOutputStream bs;
    private static PrintStream ps;
    private static long[] times = new long[2];
    private static String[] res = new String[2];
    private static StringBuilder input;
    public static void main(String[] args) throws Exception {
        //TODO Modify the expected number for run and comparison
        while(expect-->0) {
            generateInputData();
            try {
                runJava(SleepyVince.class, input, 0);
//                runCpp("cpps\\myC-2.exe",input,0);
                runCpp("cpps\\labD-2.exe", input, 1);

                System.setOut(OUT);
                System.gc();
                if (isSuccess()) {
                    onSuccess(input);
                } else {
                    onWrong(input);
                    break;
                }
            } catch (Exception e) {
                onErr(input, e);
                break;
            }
        }
    }

    /**
     * Execute when RE
     * @param input the input data
     * @param e the exception
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
     * @param input the input data
     * @throws Exception
     */
    private static void onWrong(StringBuilder input) throws Exception{
        System.err.println("Wrong, read the test.txt");
        PrintWriter pw = new PrintWriter("test.txt");
        pw.println(input.toString());
        pw.println("Result0:\n"+res[0] + " \nResult1:\n" + res[1]);
        pw.close();
    }

    /**
     * Execure when success
     * @param input the input data
     * @throws Exception
     */
    private static void onSuccess(StringBuilder input) throws Exception {
        System.out.println("Success! Count:" + count++ + " Time0： " + times[0] + " Time1: " + times[1]);
        System.out.println("Result: "+res[0]);
    }

    /**
     * Cpp exe runner
     * @param exe the exe path
     * @param input input data
     * @param index the task index 0 or 1
     * @throws Exception
     */
    private static void runCpp(String exe,StringBuilder input,int index) throws Exception {
        Runtime rt = Runtime.getRuntime();
        Files.writeString(Path.of("in.txt"),input.toString());
        String[] cmd = { "cmd.exe", "/c", exe+" <in.txt >result.txt" };
        Process pB = rt.exec(cmd);
        long start = System.currentTimeMillis();
        pB.waitFor();
        long stop = System.currentTimeMillis();
        res[index] = Files.readString(Path.of("result.txt")).trim();
        times[index] = stop - start;
    }

    /**
     * Java runner
     * @param task the task class
     * @param input input data
     * @param index the task index 0 or 1
     * @throws IOException
     */
    private static void runJava(Class task,StringBuilder input,int index) throws Exception {
        redirect(input);
        Method m = task.getMethod("main",String[].class);
        long start = System.currentTimeMillis();
        m.invoke(null,(Object)new String[]{});
        long stop = System.currentTimeMillis();
        res[index] = bs.toString().trim();
        times[index] = stop - start;
    }

    /**
     * Input generator
     * @return void
     */
    private static void generateInputData() {
        var r = new Random();
//        var n = r.nextInt(1000000)+1;
//        var m = r.nextInt(1000000)+1;
        var n = 100000;
        input = new StringBuilder();
        input.append(n).append("\n");
        for (int i = 0; i < n; i++) {
//            int a = (int)(Math.random()*200)+1;
//            if(Math.random()<0.8)
//                a = -1;
            var a = -1;
            input.append(a).append(" ");
        }
        input.append("\n");
    }

    /**
     * Java Input/Output redirect
     * @param the input data
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
