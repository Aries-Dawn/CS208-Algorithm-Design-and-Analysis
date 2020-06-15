package lab10;

import java.io.IOException;
import java.util.Scanner;

public class test {

    public static void main(String[] args) throws IOException {
//        System.out.println(getAngle(0,2,-1,1));
//        System.out.println(getAngle(2,2,0,0));
//        System.out.println(getAngle(-1,1,0,2));
//        TheNearestPointPair.Pair c
//                = new TheNearestPointPair.Pair(
//                        new TheNearestPointPair.Point(2,new int[]{1058857,30053433},1),
//                new TheNearestPointPair.Point(2,new int[]{1721390,39688756},1));
//        TheNearestPointPair.Pair c1
//                = new TheNearestPointPair.Pair(
//                new TheNearestPointPair.Point(2,new int[]{26832307,219413},1),
//                new TheNearestPointPair.Point(2,new int[]{27491456,222697},1));
//        System.out.println(c.distance + " " + c1.distance);
//        System.out.println(TheNearestPointPair.abs(5));;

    }
    private static double getAngle
            (int targetX,int targetY,int currentX,int currentY){
        double angle;
        int x = currentX - targetX;
        int y = currentY - targetY;
        angle = Math.atan2(x, y)*180.0/Math.PI;
        return angle < 0? angle + 360 : angle;
    }

//    private static double getAngle
//            (int targetX,int targetY,int currentX,int currentY){
//        double angle;
//        int x = targetX - currentX;
//        int y = targetY - currentY;
//        angle = Math.atan2(x, y)*180.0/Math.PI;
//        return angle < 0? angle + 360 : angle;
//    }

}
