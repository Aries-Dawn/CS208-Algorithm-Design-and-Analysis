package lab1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        People[] man    = new People[size];
        People[] woman  = new People[size];
        for (int i = 0;i < size;i++){
            String name = sc.next();
            man[i] = new People(sc,i,name,size);
        }
        for (int i = 0;i < size;i++){
            String name = sc.next();
            woman[i] = new People(sc,i,name,size);
        }
        Queue<People> manList = new PriorityQueue<>(new Comparator<People>() {
            @Override
            public int compare(People o1, People o2) {
                return o1.index - o2.index;
            }
        });
        manList.addAll(Arrays.asList(man).subList(0, size));
        while (!manList.isEmpty()){
            People temp = manList.poll();
            stable(manList,man,temp,woman);
        }
        for (People p : man)
            System.out.println(p.name + " " + p.HW.name);
    }

    private static void stable(Queue<People> free,People[] manList, People man, People[] woman){
        int index = 0;
        flag:
        for (int i = 0;i < man.lover.length;i++){
            if (man.lover[i] != null){
                String name = man.lover[i];
                for (int j = 0;j < woman.length;j++){
                    if (woman[j].name.equals(name)){
                        index = j;
                        man.lover[i] = null;
                        break flag;
                    }
                }
            }
        }
        People w = woman[index];
        if (w.isFree){
            man.isFree = false;
            w.isFree = false;
            man.HW = w;
            w.HW = man;
            for (int j = 0;j < w.lover.length;j++){
                if (w.lover[j].equals(man.name)) {
                    w.indexOfHusband = j;
                    break;
                }
            }
        }
        else {
            int manIndex = 0;
            for (int j = 0;j < w.lover.length;j++){
                if (w.lover[j].equals(man.name))
                    manIndex = j;
            }
            if (manIndex < w.indexOfHusband){
                People temp = manList[w.HW.index];
                man.isFree = false;
                w.isFree = false;
                man.HW = w;
                w.HW = man;
                temp.HW = null;
                temp.isFree = true;
                free.add(temp);
            }
            else {
                free.add(man);
            }

        }
    }

}

class People{
    int index;
    int indexOfHusband;
    String name;
    boolean isFree;
    People HW;
    String[] lover;

    People(Scanner sc,int index, String name,int size){
        this.index = index;
        this.name = name;
        isFree = true;
        HW = null;
        lover = new String[size];
        addLover(sc);
    }

    void addLover(Scanner sc){
        for (int i = 0;i < lover.length;i++){
            String s = sc.next();
            lover[i] = s;
        }
    }
}
