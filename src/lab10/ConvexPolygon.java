package lab10;

import java.io.*;
import java.util.*;

public class ConvexPolygon {

    static int size;
    static Point[] all;
    static long x,y;
    static long minY;
    static long target_x,target_y;
    static long current_x,current_y;
    static int point_index;
    static double a,b,c;
    //a is index of y,b is index of x,c is a constant
    static double temp_angle;
    static double minAngle = -1;
    static double maxAngle = -1;
    static long countMin = 0,countMax = 0;
    static Point temp;
    static Point smallestPoint;
    static Point pre,pre2;
    static double distance1,distance2;
    static Map<Double,Point> choose = new HashMap<>();
    static Stack<Point> fin = new Stack<>();
    static PriorityQueue<Point> firstChoose
            = new PriorityQueue<>(new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            return (int)(o1.angle*10000000 - o2.angle*10000000);
        }
    });

    public static void main(String[] args) throws IOException {
        Reader sc=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        size = sc.nextInt();
        all = new Point[size];
        for (int i = 0;i < size;i++){
            x = sc.nextInt();
            y = sc.nextInt();
            if (i == 0) {
                minY = y;
                point_index = i;
            }
            else {
                if (minY > y) {
                    minY = y;
                    point_index = i;
                }
            }
            all[i] = new Point(x,y);
        }
        smallestPoint = all[point_index];
        target_x = smallestPoint.x;
        target_y = smallestPoint.y;
        chooseFirst();
        for (Double k : choose.keySet())
            firstChoose.add(choose.get(k));
        fin.add(all[point_index]);
        fin.add(firstChoose.poll());
        fin.add(firstChoose.poll());
        for (int i = 0;i < all.length;i++){
            if (i != point_index && all[i].angle == minAngle){
                countMin++;
            }
            if (minAngle != maxAngle){
                if (i != point_index && all[i].angle == maxAngle){
                    countMax++;
                }
            }
        }
        countMax -= 1;
        countMin -= 1;
        while (!firstChoose.isEmpty()){
            temp = firstChoose.poll();
            add(temp);
        }

        out.println(fin.size() + countMin + countMax);

        out.close();
    }

    private static void add(Point adder){
        pre = fin.pop();
        pre2 = fin.peek();
        boolean inside = isInside(smallestPoint,pre,adder,pre2);
        if (inside && fin.size() >= 3)
            add(adder);
        else {
            if (!inside) {
                fin.add(pre);
            }
            fin.add(adder);
        }
    }


    private static boolean isInside
            (Point target,Point older,Point newer,Point oldest){
        a = (double)newer.x - (double)oldest.x;
        b = (double)oldest.y - (double)newer.y;
        c = (double)newer.y * (double)oldest.x - (double)oldest.y * (double)newer.x;
        return (a * older.y + b * older.x + c) * (a * target.y + b * target.x + c) > 0;
    }

    private static void chooseFirst(){
        for (int i = 0;i < size;i++){
            if (point_index != i){
                current_x = all[i].x;
                current_y = all[i].y;
                temp_angle = getAngle(target_x,target_y,current_x,current_y);
                if (minAngle == -1)
                    minAngle = temp_angle;
                else if (minAngle > temp_angle)
                    minAngle = temp_angle;
                if (maxAngle == -1)
                    maxAngle = temp_angle;
                else if (maxAngle < temp_angle)
                    maxAngle = temp_angle;
                all[i].angle = temp_angle;
                if (choose.containsKey(temp_angle)){
                    temp = choose.get(temp_angle);
                    distance1 = getDistance(target_x,target_y,temp.x,temp.y);
                    distance2 = getDistance(target_x,target_y,current_x,current_y);
                    if (distance2 > distance1)
                        choose.replace(temp_angle,all[i]);
                }
                else
                    choose.put(temp_angle,all[i]);
            }
        }
    }

    private static double getDistance
            (long targetX,long targetY,long currentX,long currentY){
        long x = currentX - targetX;
        long y = currentY - targetY;
        return Math.sqrt(x*x+y*y);
    }


    private static double getAngle
            (long targetX,long targetY,long currentX,long currentY){
        double angle;
        long x = currentX - targetX;
        long y = currentY - targetY;
        angle = Math.atan2(y, x)*180.0/Math.PI;
        return angle < 0? angle + 180 : angle;
    }


    static class Point{
        long x,y;
        double angle;
        Point(long x,long y){
            this.x=x;
            this.y=y;
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
