package oracle.mooc.jvm.troubleshooting.week2;

import java.util.*;


public class OOM {
    
    public static void main(String[] args) {
        
        ArrayList<Double> a0 = new ArrayList();
        ArrayList<Integer> a1 = new ArrayList();
        ArrayList<String> a2 = new ArrayList();
        
        int i=0;

        String s = "hello";

        while (true) {
            a0.add(new Double(0.0));
            a1.add(new Integer(1));
            a2.add(new String(s));
            i++;
        }
        
    }
    
}