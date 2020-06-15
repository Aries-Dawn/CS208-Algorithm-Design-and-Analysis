package lab1;

import java.util.*;

public class Main2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        Man[] man    = new Man[size];
        Woman[] woman  = new Woman[size];
        HashMap<String,Integer> allPeopleName = new HashMap<>();
        for (int i = 0;i < size;i++){
            man[i] = new Man(i,sc.next());
            allPeopleName.put(man[i].name,i);
            man[i].loved = new String[size];
            for (int j = 0;j < size;j++) {
                String s = sc.next();
                man[i].loved[j] = s;
            }
        }

        for (int i = 0;i < size;i++){
            woman[i] = new Woman(i,sc.next());
            allPeopleName.put(woman[i].name,i + size);
            for (int j = 0;j < size;j++) {
                String s = sc.next();
                woman[i].lover.put(s, j);
            }
        }

        Queue<Man> manList = new PriorityQueue<>(Comparator.comparingInt(o -> o.index));
        manList.addAll(Arrays.asList(man).subList(0, size));
        while (!manList.isEmpty()){
            Man temp = manList.poll();
            stable(allPeopleName,manList,man,temp,woman);
        }
        for (Man p : man)
            System.out.println(p.name + " " + p.HW);
    }

    private static void stable(HashMap<String,Integer> allPeopleName,Queue<Man> free,Man[] manList, Man man, Woman[] woman){
        int index = 0;
        int size = manList.length;
        for (int i = 0;i < size;i++){
            if (man.loved[i] != null){
                String name = man.loved[i];
                index = allPeopleName.get(name) % size;
                man.loved[i] = null;
                break;
            }
        }
        Woman w = woman[index];
        if (w.isFree){
            man.isFree = false;
            w.isFree = false;
            man.HW = w.name;
            w.HW = man.name;
        }
        else if (w.lover.get(man.name) < w.lover.get(w.HW)){
                Man temp = manList[allPeopleName.get(w.HW)];
                man.isFree = false;
                w.isFree = false;
                man.HW = w.name;
                w.HW = man.name;
                temp.HW = null;
                temp.isFree = true;
                free.add(temp);
        }
        else {
                free.add(man);
        }
    }

    static class Man{
        int index;
        String name;
        boolean isFree;
        String HW;
        String[] loved;

        Man(int index, String name){
            this.index = index;
            this.name = name;
            isFree = true;
            HW = null;
        }
    }

    static class Woman{
        int index;
        String name;
        boolean isFree;
        String HW;
        HashMap<String,Integer> lover;

        Woman(int index, String name){
            this.index = index;
            this.name = name;
            isFree = true;
            HW = null;
            lover = new HashMap<>();
        }
    }

}
