package lab3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class Evaluator {
    static int count = 0;
    public static void main(String[] args) throws IOException, InterruptedException {
        Random r = new Random();
        int n = 400000;

        StringBuilder sb = new StringBuilder();
        sb.append(n+"\n");
        for(int i=0;i<n;i++){
            sb.append(r.nextInt(1000000000)+" ");
//            sb.append("1000000000 ");
        }
        sb.append("\n");
        ByteArrayInputStream in = new ByteArrayInputStream(sb.toString().getBytes());
        System.setIn(in);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bs);
        final PrintStream OUT = System.out;
        System.setOut(ps);

        long start = System.currentTimeMillis();
        P2_fast.main(null);
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

        Process pB= rt.exec("B.exe");
        pB.getOutputStream().write(sb.toString().getBytes());
        pB.getOutputStream().flush();
        start = System.currentTimeMillis();
        byte[] bytes = new byte[1000];
        pB.waitFor();
        pB.getInputStream().read(bytes);
        stop = System.currentTimeMillis();
        String re1 = new String(bytes).trim();
//        datatest.main(null);
        long cyfTime = stop - start;
        System.setOut(OUT);
        if(re0.equals(re1)){
            System.out.println(true+" "+count+++" myTime: "+myTime+" cyfTime: "+cyfTime);
            main(null);
        }else{
            System.out.println(re0+" "+re1);
//            System.out.println(sb.toString());
        }
    }

    private static void shuffle(int n, String[] boys, String[] girls, StringBuilder sb) {
        for(int i=0;i<n;i++){
            sb.append(girls[i]+" ");
            List<String> b = Arrays.asList(boys.clone());
            Collections.shuffle(b);
            for(int j=0;j<n;j++){
                sb.append(b.get(j)+" ");
            }
            sb.append("\n");
        }
    }
}
