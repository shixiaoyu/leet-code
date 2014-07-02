package aleczhang.leetcode;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.junit.Test;

public class playground {

    @Test
    public void test() {

        assertEquals(7, solution(6, new int[] { 1, 8, -3, 0, 1, 3, -2, 4, 5 }));
        assertEquals(16, solution(0, new int[] { 0, 0, 0, 0 }));
        assertEquals(25, solution(6, new int[] { 3, 3, 3, 3, 3 }));
        assertEquals(25, solution(6, new int[] { 3, 3, 3, 3, 3 }));
        assertEquals(25, solution(6, new int[] { 3, 3, 3, 3, 3 }));
    }

    public int solution(int K, int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i : A) {
            if (map.containsKey(i)) {
                int originalCount = map.get(i);
                map.put(i, originalCount + 1);
            }
            else {
                map.put(i, 1);
            }
        }
        int result = 0;
        for (Integer i : map.keySet()) {
            if (i * 2 != K) {
                if (map.containsKey(K - i)) {
                    result += map.get(K - i) * map.get(i);
                }
            }
            else {
                result += map.get(i) * map.get(i);
            }
        }
        return result;
    }

    private int twoSum(int value, int[] arr) {
        int sum = 0;
        int len = arr.length;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i : arr) {
            if (2 * i == value) {
                sum++;
            }
            list.add(i);
        }
        Collections.sort(list);
        int left = 0, right = len - 1;
        while (left < right) {
            if (list.get(left) == list.get(right) && 2 * list.get(left) == value) {
                int times = right - left + 1;
                sum += times * (times - 1);
                break;
            }
            else if (list.get(left) + list.get(right) == value) {
                int leftCount = 1, rightCount = 1;
                left++;
                right--;
                while (list.get(left - 1) == list.get(left)) {
                    leftCount++;
                    left++;
                }
                while (list.get(right - 1) == list.get(right)) {
                    rightCount++;
                    right--;
                }
                sum += leftCount * rightCount * 2;
            }
            else if (list.get(left) + list.get(right) < value) {
                left++;
            }
            else {
                right--;
            }
        }
        return sum;
    }
}
