package lab6;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        String string;
        Scanner input =new Scanner(System.in );
        System.out.print("Enter: ");
        string=input.nextLine();
        System.out.println(string.replace(" ", ""));
        String str=string.replace(" ", "");
        int m=str.length() ;
        System.out.println(m);
        double n0=Math.ceil(Math.sqrt(m));
        int n=(int)n0;
        System.out.println(n);
        double p=n*(n-1);
        if(p>m){
            char [][]matrix=new char [n][n-1];
            for (int k=0;k<p;k++){
            for (int i=0;i<n;i++){
                for(int j=0;j<(n-1);j++){
                    matrix [i][j]=str.charAt(k);
                    System.out.print(matrix [i][j]);
                }
                System.out.println();
            }}
        }else{
            char [][]matrix=new char [n][n];

            for (int i=0;i<n;i++){
                for(int j=0;j<(n);j++){
                    for (int k=0;k<p;k++){
                    matrix [i][j]=str.charAt(k);
                    System.out.print(matrix [i][j]);
                }
                System.out.println();
            }
        }
    }
}}
