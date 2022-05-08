package Homework;
import java.util.*;
public class H1 {
    public static void main(String[] args) {
        System.out.println("202012900245 张廷晴");
        Scanner scanner =new Scanner(System.in);
        int m = scanner.nextInt();
        List<Integer> list = new LinkedList<Integer>();
        Set<Integer> set  = new HashSet<Integer>();
        for(int i=0;i<m;i++) {
            int x = scanner.nextInt();
            list.add(i,x);
            set.add(x);
        }
        System.out.println("这是list里面的数");
        Iterator<Integer> it = list.iterator();
        while(it.hasNext()) {
            System.out.printf(it.next()+" ");
        }
        System.out.println();
        System.out.println("这是set里面的数");
        for (int j :set) {
            Integer i=(Integer)j;
            System.out.printf(i+" ");
        }
    }
}



