package lab5;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class test {
    public static void main (String args[]){
        String firstcity;
        String secondcity;
        String threecity;
        String[]input= new String[3];
        Scanner in =new Scanner(System.in );
        System.out.print("Enter the first city: ");
        firstcity =in.nextLine();
        input [0]=firstcity;
        System.out.print("Enter the second city: ");
        secondcity =in.nextLine() ;
        input [1]=secondcity;
        System.out.print("Enter the three city: ");
        threecity =in.nextLine() ;
        input [2]=threecity;

        List<String> list = (List<String>) Arrays.asList(input );
        Collections.sort(list);
        System.out.print("The three cities in alphabetical order are");
        int j;

        for (int i = 0;i < list.size();i++){
            System.out.print(" " + list.get(i));
        }
    }
}
