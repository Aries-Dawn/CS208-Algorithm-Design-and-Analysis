package lab10;

import java.io.*;
import java.util.*;

public class TheNearestPointPair {

    private static Point[] all;
    private static ArrayList<Point> Sy;
    private static double d_min;
    private static int y_min,size;
    private static Pair pair_min;
    private static Pair temp;
    private static Pair[] outs;
    private static Point[] s1;
    private static Point[][] s2;

    public static void main(String[] args) throws IOException {
        Reader sc=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        Sy = new ArrayList<>();
        d_min = 0;
        pair_min = null;
        temp = null;
        int max = Integer.MAX_VALUE;
        y_min = max;
        int inx,iny,inz;
        int n = sc.nextInt();
        int degree = sc.nextInt();
        all = new Point[n];
        for (int i = 0;i < n;i++){
            if (degree == 1){
                inx = sc.nextInt();
                all[i] = new Point(degree,i,inx);
            }
            else if (degree == 2){
                inx = sc.nextInt();
                iny = sc.nextInt();
                if (y_min == max)
                    y_min = iny;
                else if (y_min > iny)
                    y_min = iny;
                all[i] = new Point(degree,i,inx,iny);
            }
            else {
                inx = sc.nextInt();
                iny = sc.nextInt();
                if (y_min == max)
                    y_min = iny;
                else if (y_min > iny)
                    y_min = iny;
                inz = sc.nextInt();
                all[i] = new Point(degree,i,inx,iny,inz);
            }

        }
        Pair result;
        if (degree == 1)
            result = one_degree_closest_Pair();
        else if (degree == 2)
            result = two_degree_closest_Pair();
        else
            result = three_degree_closest_Pair();
        result.order();
        if (degree == 1){
            out.println(result.a.x);
            out.println(result.b.x);
        }
        else if (degree == 2){
            out.print(result.a.x);
            out.print(" ");
            out.println(result.a.y);
            out.print(result.b.x);
            out.print(" ");
            out.println(result.b.y);
        }
        else {
            out.print(result.a.x);
            out.print(" ");
            out.print(result.a.y);
            out.print(" ");
            out.println(result.a.z);
            out.print(result.b.x);
            out.print(" ");
            out.print(result.b.y);
            out.print(" ");
            out.println(result.b.z);

        }
        out.close();
    }

    private static Pair three_degree_closest_Pair(){
        Arrays.sort(all, Comparator.comparingInt(o -> o.x));
        Point[] Px = Arrays.copyOf(all,all.length);
        for (int i = 0;i < Px.length;i++){
            Px[i].rank = i;
        }
        Arrays.sort(all, Comparator.comparingInt(o -> o.z));
        Point[] Pz = Arrays.copyOf(all,all.length);
        return closest_three(Px,Pz);

    }

    private static Pair closest_three(Point[] Px,Point[] Pz){
        if (Px.length == 2)
            return new Pair(Px[0],Px[1]);
        if (Px.length == 3){
            return getPair(Px);
        }

        Point[] Qx = new Point[Px.length / 2];
        Point[] Rx = new Point[Px.length - Qx.length];
        for (int i = 0;i < Px.length;i++){
            if (i < Qx.length)
                Qx[i] = Px[i];
            else
                Rx[i - Qx.length] = Px[i];
        }
        Pair QQ = closest_three(Qx,Pz);
        Pair RR = closest_three(Rx,Pz);
        s1 = new Point[Px.length + 1];
        s2 = new Point[Px.length + 1][Px.length + 1];
        QQ.calculateDistance();
        RR.calculateDistance();
        double aerfa = Math.min(QQ.distance,RR.distance);
        int x = Qx[Qx.length - 1].x;
        int index = 0;
        for (Point c : Pz){
            int tt;
            if (abs(c.x - x) <= aerfa
                    && c.rank <= Qx[Qx.length - 1].rank){
                tt = (int)((c.y - y_min + 1) /aerfa) / 2;
                s1[index] = c;
                index++;
                if (tt == 0) {
                    c.search1 = 0;
                    c.search2 = 1;
                }
                else {
                    c.search1 = tt - 1;
                    c.search2 = tt;
                }
                if (c.search1 < s2.length){
                    if (c.search1 != -1){
                        size = 0;
                        for (int i = 0;i < s2[c.search1].length;i++){
                            if (s2[c.search1][i] == null)
                                break;
                            else
                                size++;
                        }
                        c.loc1 = size;
                    }
                }
                if (c.search2 < s2.length){
                    if (c.search2 != -1){
                        size = 0;
                        for (int i = 0;i < s2[c.search2].length;i++){
                            if (s2[c.search2][i] == null)
                                break;
                            else
                                size++;
                        }
                        c.loc2 = size;
                    }
                }
            }
            if (abs(c.x - x) <= aerfa
                    && c.rank > Qx[Qx.length - 1].rank){
                tt = (int)((c.y - y_min) / aerfa) / 2;
                for (int i = 0;i < s2[tt].length;i++){
                    if (s2[tt][i] == null){
                        s2[tt][0] = c;
                        break;
                    }
                }
            }
        }
        d_min = aerfa;
        pair_min = null;
        for (Point c : s1){
            if (c == null)
                break;
            int count = 0;
            if (c.search1 != -1 ){
                size = 0;
                for (int i = 0;i < s2[c.search1].length;i++){
                    if (s2[c.search1][i] == null)
                        break;
                    else
                        size++;
                }
                for (int i = 0;i < size;i++){
                    if (abs(s2[c.search1][i].z - c.z) <= aerfa){
                        temp = new Pair(s2[c.search1][i],c);
                        temp.calculateDistance();
                        if (d_min > temp.distance) {
                            d_min = temp.distance;
                            pair_min = temp;;
                            count++;
                        }
                        if (count > 24)
                            break;
                    }
                }
            }
            if (c.search2 != -1){
                size = 0;
                for (int i = 0;i < s2[c.search2].length;i++){
                    if (s2[c.search2][i] == null)
                        break;
                    else
                        size++;
                }
                for (int i = 0;i < size;i++){
                    if (abs(s2[c.search2][i].z - c.z) <= aerfa){
                        temp = new Pair(s2[c.search2][i],c);
                        temp.calculateDistance();
                        if (d_min > temp.distance) {
                            d_min = temp.distance;
                            pair_min = temp;;
                            count++;
                        }
                        if (count > 24)
                            break;
                    }
                }
            }
        }
        if (pair_min != null)
            return pair_min;
        else if (QQ.distance < RR.distance)
            return QQ;
        else
            return RR;


    }

    private static int abs(int a){
        return a > 0?a:(-1 * a);
    }

    private static Pair two_degree_closest_Pair(){
        Arrays.sort(all, Comparator.comparingInt(o -> o.x));
        Point[] Px = Arrays.copyOf(all,all.length);
        Arrays.sort(all, Comparator.comparingInt(o -> o.y));
        Point[] Py = Arrays.copyOf(all,all.length);
        return closest_two(Px,Py);
    }

    private static Pair closest_two(Point[] Px,Point[] Py){
        if (Px.length == 2) {
            Pair p =  new Pair(Px[0], Px[1]);
            p.calculateDistance();
            return p;
        }
        if (Px.length == 3){
            return getPair(Px);
        }

        Point[] Qx = new Point[Px.length / 2];
        Point[] Rx = new Point[Px.length - Qx.length];
        for (int i = 0;i < Px.length;i++){
            if (i < Qx.length)
                Qx[i] = Px[i];
            else
                Rx[i - Qx.length] = Px[i];
        }
        Pair QQ = closest_two(Qx,Py);
        Pair RR = closest_two(Rx,Py);
        QQ.calculateDistance();
        RR.calculateDistance();
        double aerfa = Math.min(QQ.distance,RR.distance);
        int x = Qx[Qx.length - 1].x;
        Sy = new ArrayList<>();
        for (Point point : Py) {
            int di = point.x - x;
            if (di <= aerfa && di >= (-1 * aerfa))
                Sy.add(point);
        }
        d_min = aerfa;
        pair_min = null;
        for (int i = 0;i < Sy.size();i++){
            if (Sy.get(i).x <= x){
                for (int j = 0;j < Sy.size();j++){
                    if (Sy.get(j).x >= x && Sy.get(j).index != Sy.get(i).index
                            && abs(Sy.get(j).y - Sy.get(i).y) <= aerfa){
                        temp = new Pair(Sy.get(i),Sy.get(j));
                        temp.calculateDistance();
                        if (d_min > temp.distance) {
                            d_min = temp.distance;
                            pair_min = temp;;
                        }
                    }
                }
            }
        }

        if (pair_min != null)
            return pair_min;
        else if (QQ.distance < RR.distance)
            return QQ;
        else
            return RR;


    }

    private static Pair one_degree_closest_Pair(){
            Arrays.sort(all, Comparator.comparingInt(o -> o.x));
            return closest_one(all);
    }

    private static Pair closest_one(Point[] target){

        if (target.length == 2)
            return new Pair(target[0],target[1]);
        if (target.length == 3){
            return getPair(target);
        }

        Point[] Q = new Point[target.length / 2];
        Point[] R = new Point[target.length - Q.length];
        for (int i = 0;i < target.length;i++){
            if (i < Q.length)
                Q[i] = target[i];
            else
                R[i - Q.length] = target[i];
        }
        Pair QQ = closest_one(Q);
        Pair RR = closest_one(R);
        QQ.calculateDistance();
        RR.calculateDistance();
        double aerfa = Math.min(QQ.distance,RR.distance);
        Pair connection = new Pair(Q[Q.length-1],R[0]);
        connection.calculateDistance();
        if (connection.distance < aerfa)
            return connection;
        else if (QQ.distance <= RR.distance)
            return QQ;
        else
            return RR;
    }

    private static Pair getPair(Point[] target) {
        Pair fi = new Pair(target[0],target[1]);
        Pair se = new Pair(target[0],target[2]);
        Pair th = new Pair(target[1],target[2]);
        fi.calculateDistance();
        se.calculateDistance();
        th.calculateDistance();
        if (fi.distance < se.distance)
            if (fi.distance < th.distance)
                return fi;
            else
                return th;
        else
        if (se.distance < th.distance)
            return se;
        else
            return th;
    }

    public static class Pair{
        Point a;
        Point b;
        double distance;

        Pair(Point a,Point b){
            this.a = a;
            this.b = b;
        }

        private void order(){
            if (a.degree == 1) {
                if (a.x > b.x) {
                    int x = b.x;
                    b.x = a.x;
                    a.x = x;
                }
            }
            else if (a.degree == 2){
                if (a.x > b.x){
                    int x = b.x;
                    int y = b.y;
                    b.x = a.x;
                    b.y = a.y;
                    a.x = x;
                    a.y = y;
                }
                else if (a.x == b.x){
                    if (a.y > b.y){
                        int y = b.y;
                        b.y = a.y;
                        a.y = y;
                    }
                }
            }
            else {
                if (a.x > b.x){
                    int x = b.x;
                    int y = b.y;
                    int z = b.z;
                    b.x = a.x;
                    b.y = a.y;
                    b.z = a.z;
                    a.x = x;
                    a.y = y;
                    a.z = z;
                }
                else if (a.x == b.x){
                    if (a.y > b.y){
                        int y = b.y;
                        int z = b.z;
                        b.y = a.y;
                        b.z = a.z;
                        a.y = y;
                        a.z = z;
                    }
                    else if (a.y == b.y){
                        if (a.z > b.z){
                            int z = b.z;
                            b.z = a.z;
                            a.z = z;
                        }
                    }
                }
            }

        }

        private void calculateDistance(){
            double sum ;
            if (a.degree == 1){
                sum = (double) (a.x - b.x) * (a.x - b.x);
            }
            else if (a.degree == 2){
                sum = (double) (a.x - b.x) * (a.x - b.x)
                        + (double) (a.y - b.y) * (a.y - b.y);
            }
            else {
                sum = (double) (a.x - b.x) * (a.x - b.x)
                        + (double) (a.y - b.y) * (a.y - b.y)
                        + (double) (a.z - b.z) * (a.z - b.z);

            }
            distance =  Math.sqrt(sum);
        }
    }

    public static class Point{
        int degree;
        int x;
        int y;
        int z;
        int index;
        int rank;
        int search1 = -1;
        int search2 = -1;
        int loc1 = 0;
        int loc2 = 0;


        Point(int degree,int index,int x){
            this.degree = degree;
            this.x = x;
            this.index = index;
        }
        Point(int degree,int index,int x,int y){
            this.degree = degree;
            this.x = x;
            this.y = y;
            this.index = index;
        }
        Point(int degree,int index,int x,int y,int z){
            this.degree = degree;
            this.x = x;
            this.y = y;
            this.z = z;
            this.index = index;
        }
    }

    static class Reader
    {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException
        {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1)
            {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do
            {
                ret = ret * 10 + c - '0';
            }  while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException
        {
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

        public double nextDouble() throws IOException
        {
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

            if (c == '.')
            {
                while ((c = read()) >= '0' && c <= '9')
                {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }

}
