package aleczhang.leetcode.util;

import java.util.ArrayList;

public class LeetCodeutilities {

    public static ArrayList<Integer> copyArrayList(ArrayList<Integer> list) {
        ArrayList<Integer> newList = new ArrayList<>();
        for (Integer i : list) {
            newList.add(i);
        }
        return newList;
    }

    public static void printArrayList(ArrayList<Integer> list) {
        for (Integer i : list) {
            System.out.print(i + ":");
        }
        System.out.println();
    }

}
