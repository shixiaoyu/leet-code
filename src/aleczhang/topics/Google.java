package aleczhang.topics;

public class Google {
    // Max sub array difference
    // Given a array of int, find two sub-arrays which doesn't overlap but consequent
    // Find the sub-arrays with the maximun sum difference
    // e.g. [2,-1,-2,1,-4,2,8]
    // A=[-1,-2,1,-4] B=[2,8]
    // return abs(-6-10)=16
    public int maxSubArrayDiff(int[] array) {
        int len = array.length;
        int[][] left = new int[2][len];
        int[][] right = new int[2][len];
        left[0][0] = array[0];
        left[1][0] = array[0];
        right[0][len - 1] = array[len - 1];
        right[1][len - 1] = array[len - 1];
        for (int i = 1; i < len; i++) {
            left[0][i] = Math.min(array[i], left[0][i - 1] + array[i]);
            left[1][i] = Math.max(array[i], left[0][i - 1] + array[i]);
            right[0][len - 1 - i] = Math.min(array[len - 1 - i], right[0][len - i] + array[len - 1 - i]);
            right[1][len - 1 - i] = Math.max(array[len - 1 - i], right[0][len - i] + array[len - 1 - i]);
        }
        int max = Math.max(right[1][0], left[1][len - 1]);
        int localMax;
        for (int i = 0; i < len - 1; i++) {
            localMax = Math.max(right[1][i + 1] - left[0][i], left[1][i] - right[0][i + 1]);
            max = Math.max(max, localMax);
        }
        return max;
    }
}
