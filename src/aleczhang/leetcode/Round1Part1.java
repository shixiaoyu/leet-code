package aleczhang.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import aleczhang.leetcode.datatypes.ListNode;
import aleczhang.leetcode.datatypes.RandomListNode;
import aleczhang.leetcode.datatypes.TreeLinkNode;
import aleczhang.leetcode.datatypes.TreeNode;
import aleczhang.leetcode.datatypes.UndirectedGraphNode;

@SuppressWarnings("unchecked")
public class Round1Part1 {

    // Word Break I
    public boolean wordBreak(String s, Set<String> dict) {
        HashMap<String, Boolean> map = new HashMap<String, Boolean>();
        return wordBreak(s, dict, map);
    }

    private boolean wordBreak(String s, Set<String> dict, HashMap<String, Boolean> known) {
        if (s.length() == 0) {
            return true;
        }
        if (known.containsKey(s)) {
            return known.get(s);
        }
        for (int i = 0; i < s.length() + 1; i++) {
            if (dict.contains(s.substring(0, i))) {
                if (!wordBreak(s.substring(i), dict, known)) {
                    known.put(s.substring(i), false);
                }
                else {
                    known.put(s.substring(i), true);
                    return true;
                }
            }
        }
        known.put(s, false);
        return false;
    }

    // Copy a list linked whose node has a random pointer
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return null;
        }
        HashMap<RandomListNode, RandomListNode> map = new HashMap<RandomListNode, RandomListNode>();
        RandomListNode newHead = new RandomListNode(head.label);
        map.put(head, newHead);
        RandomListNode runner = head;
        RandomListNode newRunner = newHead;
        // Always check runner.next!=null before initialize newRunner.next
        // newRunner cannot point to curNode as it may be null, must point to curNode's parent
        while (runner.next != null) {
            newRunner.next = new RandomListNode(runner.next.label);
            map.put(runner.next, newRunner.next);
            newRunner = newRunner.next;
            runner = runner.next;
        }
        runner = head;
        newRunner = newHead;
        while (runner != null) {
            if (runner.random != null) {
                newRunner.random = map.get(runner.random);
            }
            newRunner = newRunner.next;
            runner = runner.next;
        }
        return newHead;
    }

    // Single number
    public int singleNumber(int[] A) {
        if (A.length == 1) {
            return A[0];
        }
        HashSet<Integer> map = new HashSet<Integer>();
        for (int i : A) {
            if (map.contains(i)) {
                map.remove(i);
            }
            else {
                map.add(i);
            }
        }
        return (int) map.toArray()[0];
    }

    // Clone graph
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) {
            return null;
        }
        UndirectedGraphNode copy = new UndirectedGraphNode(node.label);
        // stores nodes we have checked
        HashSet<UndirectedGraphNode> set = new HashSet<UndirectedGraphNode>();
        // stores mapping from old graph node to new graph node
        HashMap<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<UndirectedGraphNode, UndirectedGraphNode>();
        map.put(node, copy);
        Queue<UndirectedGraphNode> queue = new LinkedList<UndirectedGraphNode>();
        queue.add(node);

        UndirectedGraphNode iterator;
        UndirectedGraphNode copyIterator;
        while (!queue.isEmpty()) {
            iterator = queue.poll();
            // if set has it, continue
            if (set.contains(iterator)) {
                continue;
            }
            // if not, get its mapping node to copyInterator and copy its every neighbor
            copyIterator = map.get(iterator);
            map.put(iterator, copyIterator);
            for (UndirectedGraphNode n : iterator.neighbors) {
                queue.add(n);
                if (map.containsKey(n)) {
                    copyIterator.neighbors.add(map.get(n));
                }
                else {
                    UndirectedGraphNode temp = new UndirectedGraphNode(n.label);
                    map.put(n, temp);
                    copyIterator.neighbors.add(temp);
                }
            }
            set.add(iterator);
        }
        return copy;
    }

    // Permutations
    public ArrayList<ArrayList<Integer>> permute(int[] num) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int n : num) {
            list.add(n);
        }
        return permute(list);
    }

    private ArrayList<ArrayList<Integer>> permute(ArrayList<Integer> nums) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        if (nums.size() == 0) {
            ArrayList<Integer> list = new ArrayList<>();
            lists.add(list);
        }
        else {
            for (int i = 0; i < nums.size(); i++) {
                Integer temp = nums.get(i);
                nums.remove(i);
                for (ArrayList<Integer> list : permute(nums)) {
                    list.add(temp);
                    lists.add(list);
                }
                nums.add(i, temp);
            }
        }
        return lists;
    }

    // Trapping Rain Water 
    // http://oj.leetcode.com/problems/trapping-rain-water/
    public int trap(int[] A) {
        int[] leftMax = new int[A.length];
        int[] rightMax = new int[A.length];
        int max = 0;
        for (int i = 0; i < A.length; i++) {
            leftMax[i] = max;
            max = A[i] > max ? A[i] : max;
        }
        max = 0;
        for (int i = A.length - 1; i > -1; i--) {
            rightMax[i] = max;
            max = A[i] > max ? A[i] : max;
        }
        int res = 0;
        for (int i = 0; i < A.length; i++) {
            int lowMax = Math.min(leftMax[i], rightMax[i]);
            if (lowMax > A[i]) {
                res += lowMax - A[i];
            }
        }
        return res;
    }

    // Combination Sum II
    // http://oj.leetcode.com/problems/combination-sum-ii/
    public ArrayList<ArrayList<Integer>> combinationSum2(int[] num, int target) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        int[] count = new int[num.length];
        ArrayList<Integer> sortedList = new ArrayList<>();
        for (int c : num) {
            sortedList.add(c);
        }
        Collections.sort(sortedList);
        int[] countLimit = new int[sortedList.size()];
        Integer value = null;
        int index = 0;
        ArrayList<Integer> cleanList = new ArrayList<>();
        for (int i = 0; i < sortedList.size(); i++) {
            if (value == null) {
                value = sortedList.get(i);
                countLimit[index]++;
                cleanList.add(sortedList.get(i));
            }
            else if (sortedList.get(i) == value) {
                countLimit[index]++;
            }
            else {
                index++;
                countLimit[index]++;
                value = sortedList.get(i);
                cleanList.add(value);
            }
        }
        int[] cleanCandidate = new int[cleanList.size()];
        for (int i = 0; i < cleanList.size(); i++) {
            cleanCandidate[i] = cleanList.get(i);
        }
        combinationSum2(cleanCandidate, count, countLimit, 0, 0, target, lists);
        return lists;
    }

    private void combinationSum2(int[] candidates, int[] count, int[] countLimit, int index, int sum, int target, ArrayList<ArrayList<Integer>> lists) {
        for (int i = index; i < candidates.length; i++) {
            if (countLimit[i] <= count[i]) {
                continue;
            }
            count[i]++;
            if (sum + candidates[i] < target) {
                combinationSum2(candidates, count, countLimit, i, sum + candidates[i], target, lists);
            }
            else if (sum + candidates[i] == target) {
                ArrayList<Integer> list = new ArrayList<>();
                for (int j = 0; j < candidates.length; j++) {
                    int times = count[j];
                    while (times > 0) {
                        list.add(candidates[j]);
                        times--;
                    }
                }
                lists.add(list);
            }
            count[i]--;
        }
    }

    // Combination Sum
    // http://oj.leetcode.com/problems/combination-sum/
    public ArrayList<ArrayList<Integer>> combinationSum(int[] candidates, int target) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        int[] count = new int[candidates.length];
        ArrayList<Integer> canList = new ArrayList<>();
        for (int c : candidates) {
            canList.add(c);
        }
        Collections.sort(canList);
        for (int i = 0; i < canList.size(); i++) {
            candidates[i] = canList.get(i);
        }
        combinationSum(candidates, count, 0, 0, target, lists);
        return lists;
    }

    private void combinationSum(int[] candidates, int[] count, int index, int sum, int target, ArrayList<ArrayList<Integer>> lists) {
        for (int i = index; i < candidates.length; i++) {
            count[i]++;
            if (sum + candidates[i] < target) {
                combinationSum(candidates, count, i, sum + candidates[i], target, lists);
            }
            else if (sum + candidates[i] == target) {
                ArrayList<Integer> list = new ArrayList<>();
                for (int j = 0; j < candidates.length; j++) {
                    int times = count[j];
                    while (times > 0) {
                        list.add(candidates[j]);
                        times--;
                    }
                }
                lists.add(list);
            }
            count[i]--;
        }
    }

    // Count and Say
    // http://oj.leetcode.com/problems/count-and-say/
    public String countAndSay(int n) {
        if (n == 1) {
            return "1";
        }
        String previous = countAndSay(n - 1);
        StringBuilder builder = new StringBuilder();
        Character ch = null;
        int num = 0;
        for (int i = 0; i < previous.length(); i++) {
            if (ch == null) {
                ch = previous.charAt(i);
                num = 1;
            }
            else if (previous.charAt(i) == ch) {
                num++;
            }
            else {
                builder.append(String.valueOf(num));
                builder.append(ch);
                ch = previous.charAt(i);
                num = 1;
            }
        }
        builder.append(num);
        builder.append(ch);
        return builder.toString();
    }

    // Search Insert Position
    // http://oj.leetcode.com/problems/search-insert-position/
    public int searchInsert(int[] A, int target) {
        if (A.length == 0) {
            return -1;
        }
        else if (A.length == 1) {
            return target <= A[0] ? 0 : 1;
        }
        return searchInsert(A, target, 0, A.length - 1);
    }

    private int searchInsert(int[] A, int target, int left, int right) {
        if (A[left] > target) {
            return left;
        }
        else if (A[right] < target) {
            return right + 1;
        }
        else if (left + 1 == right) {
            return A[left] == target ? left : right;
        }
        int mid = (left + right) / 2;
        if (A[mid] == target) {
            return mid;
        }
        else if (A[mid] > target) {
            return searchInsert(A, target, left, mid - 1);
        }
        else {
            return searchInsert(A, target, mid + 1, right);
        }
    }

    // Search for a Range
    // http://oj.leetcode.com/problems/search-for-a-range/
    public int[] searchRange(int[] A, int target) {
        ArrayList<Integer> res = new ArrayList<>();
        searchRange(A, target, 0, A.length - 1, res);
        if (res.isEmpty()) {
            return new int[] { -1, -1 };
        }
        if (res.size() == 1) {
            return new int[] { res.get(0), res.get(0) };
        }
        int[] resArray = new int[res.size()];
        int index = 0;
        for (Integer i : res) {
            resArray[index++] = i;
        }
        return resArray;
    }

    private void searchRange(int[] A, int target, int left, int right, ArrayList<Integer> res) {
        if (left == right) {
            if (A[left] == target) {
                res.add(left);
            }
            return;
        }
        else if (A[left] > target || A[right] < target) {
            return;
        }
        int mid = (left + right) / 2;
        if (A[mid] == target) {
            int leftIndex = mid - 1;
            while (leftIndex >= left && A[leftIndex] == target) {
                leftIndex--;
            }
            int rightIndex = mid + 1;
            while (rightIndex <= right && A[rightIndex] == target) {
                rightIndex++;
            }
            res.add(leftIndex + 1);
            res.add(rightIndex - 1);
        }
        else if (A[mid] > target) {
            searchRange(A, target, left, mid - 1, res);
        }
        else {
            searchRange(A, target, mid + 1, right, res);
        }
    }

    // Search in Rotated Sorted Array
    // http://oj.leetcode.com/problems/search-in-rotated-sorted-array/
    public int search(int[] A, int target) {
        if (A.length == 0) {
            return -1;
        }
        else if (A.length == 1) {
            return A[0] == target ? 0 : -1;
        }
        int i = 0;
        while (i + 1 < A.length && A[i + 1] > A[i]) {
            i++;
        }
        if (i == A.length - 1) {
            return search(A, target, 0, A.length - 1, A.length);
        }
        int[] B = new int[A.length + i + 1];
        for (int m = 0; m < B.length; m++) {
            B[m] = A[m % A.length];
        }
        return search(B, target, i + 1, B.length - 1, A.length);
    }

    private int search(int[] A, int target, int left, int right, int len) {
        if (left == right) {
            return A[left] == target ? left % len : -1;
        }
        else if (A[left] > target || A[right] < target) {
            return -1;
        }
        int mid = (left + right) / 2;
        if (A[mid] == target) {
            return mid % len;
        }
        else if (A[mid] > target) {
            return search(A, target, left, mid - 1, len);
        }
        else {
            return search(A, target, mid + 1, right, len);
        }
    }

    // Next Permutation
    // http://oj.leetcode.com/problems/next-permutation/
    public void nextPermutation(int[] num) {
        if (num.length == 1) {
            return;
        }
        int start = num.length - 1, iterator = start;
        while (iterator > 0 && num[iterator] <= num[iterator - 1]) {
            iterator--;
        }
        if (iterator == 0) {
            int temp;
            for (int i = 0; i < num.length / 2; i++) {
                temp = num[i];
                num[i] = num[num.length - 1 - i];
                num[num.length - 1 - i] = temp;
            }
            return;
        }
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = iterator - 1; i < num.length; i++) {
            list.add(num[i]);
        }
        Collections.sort(list);
        int i = 0;
        while (list.get(i) <= num[iterator - 1]) {
            i++;
        }
        num[iterator - 1] = list.get(i);
        list.remove(i);
        for (int digit : list) {
            num[iterator] = digit;
            iterator++;
        }
    }

    // Substring with Concatenation of All Words
    // http://oj.leetcode.com/problems/substring-with-concatenation-of-all-words/
    public ArrayList<Integer> findSubstring(String S, String[] L) {
        return null;
    }

    public void findSubstring(String S, String[] L, boolean[] done, int indice, int curIndice, ArrayList<Integer> list) {
        boolean result = true;
        for (boolean b : done) {
            result &= b;
        }
        if (result) {
            list.add(indice);
            return;
        }
    }

    // Divide Two Integers
    // http://oj.leetcode.com/problems/divide-two-integers/
    public int divide(int dividend, int divisor) {
        if (divisor == 0) {
            return Integer.MAX_VALUE;
        }
        else if (dividend == 0) {
            return 0;
        }
        int diviendSign = dividend > 0 ? 1 : -1;
        int divisorSign = divisor > 0 ? 1 : -1;
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);
        int base = 0;
        while (dividend >= divisor) {
            base++;
            dividend -= divisor;
        }
        return base * diviendSign * divisorSign;
    }

    // Implement strStr() 
    // http://oj.leetcode.com/problems/implement-strstr/
    public String strStr(String haystack, String needle) {
        if (haystack.isEmpty() || needle.isEmpty()) {
            return null;
        }
        for (int i = 0; i < haystack.length(); i++) {
            int j = 0;
            while (j < needle.length() && i + j < haystack.length() && haystack.charAt(i + j) == needle.charAt(j)) {
                j++;
            }
            if (j == needle.length()) {
                return haystack.substring(i);
            }
        }
        return null;
    }

    // Remove Element 
    // http://oj.leetcode.com/problems/remove-element/
    public int removeElement(int[] A, int elem) {
        if (A.length == 0) {
            return 0;
        }
        int current = 0;
        for (int runner = 0; runner < A.length; runner++) {
            if (A[runner] == elem) {
                continue;
            }
            else {
                A[current] = A[runner];
                current++;
            }
        }
        return current;
    }

    // Remove Duplicates from Sorted Array
    // http://oj.leetcode.com/problems/remove-duplicates-from-sorted-array/
    public int removeDuplicates(int[] A) {
        if (A.length == 0) {
            return 0;
        }
        int current = 0;
        for (int runner = 1; runner < A.length; runner++) {
            if (A[runner] != A[current]) {
                current++;
                A[current] = A[runner];
            }
        }
        int len = ++current;
        while (current != A.length) {
            A[current] = 0;
            current++;
        }
        return len;
    }

    // Reverse Nodes in k-Group 
    // http://oj.leetcode.com/problems/reverse-nodes-in-k-group/
    // !!! Whenever an array is used, consider if there is only one element !!!
    public ListNode reverseKGroup(ListNode head, int k) {
        if (k == 1) {
            return head;
        }
        ListNode parent = new ListNode(-1);
        parent.next = head;
        reverseOneTime(parent, k);
        return parent.next;
    }

    private void reverseOneTime(ListNode parent, int k) {
        int num = k;
        ListNode iterator = parent;
        ListNode[] nodes = new ListNode[k];
        while (iterator.next != null && num > 0) {
            iterator = iterator.next;
            num--;
            nodes[num] = iterator;
        }
        if (num > 0) {
            return;
        }
        parent.next = nodes[0];
        nodes[k - 1].next = iterator.next;
        iterator = nodes[k - 1];
        for (int i = 0; i < k - 1; i++) {
            nodes[i].next = nodes[i + 1];
        }

        reverseOneTime(iterator, k);
    }

    // Swap Nodes in Pairs
    // http://oj.leetcode.com/problems/swap-nodes-in-pairs/
    public ListNode swapPairs(ListNode head) {
        ListNode parent = new ListNode(-1), iterator = parent, temp;
        parent.next = head;
        while (iterator.next != null && iterator.next.next != null) {
            temp = iterator.next.next;
            iterator.next.next = temp.next;
            temp.next = iterator.next;
            iterator.next = temp;
            iterator = iterator.next.next;
        }
        return parent.next;
    }

    // Merge k Sorted Lists
    // http://oj.leetcode.com/problems/merge-k-sorted-lists/
    public ListNode mergeKLists(ArrayList<ListNode> lists) {
        if (lists.size() == 0) {
            return null;
        }
        ListNode init = null;
        for (ListNode list : lists) {
            init = mergeTwoLists(init, list);
        }
        return init;
    }

    // Generate Parentheses
    // http://oj.leetcode.com/problems/generate-parentheses/
    public ArrayList<String> generateParenthesis(int n) {
        ArrayList<String> list = new ArrayList<>();
        if (n == 1) {
            list.add("()");
            return list;
        }
        for (String str : generateParenthesis(n - 1)) {
            str = "(" + str;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '(') {
                    String newStr = str.substring(0, i + 1) + ")" + str.substring(i + 1);
                    if (!list.contains(newStr)) {
                        list.add(newStr);
                    }
                }
            }
        }
        return list;
    }

    // Valid Parentheses 
    // http://oj.leetcode.com/problems/valid-parentheses/
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == '{' || s.charAt(i) == '[') {
                stack.push(s.charAt(i));
            }
            else if (stack.isEmpty()) {
                return false;
            }
            else if ((s.charAt(i) == ')' && stack.peek() == '(') || (s.charAt(i) == ']' && stack.peek() == '[') || (s.charAt(i) == '}' && stack.peek() == '{')) {
                stack.pop();
            }
            else {
                return false;
            }
        }
        return stack.isEmpty();
    }

    // Remove Nth Node From End of List 
    // http://oj.leetcode.com/problems/remove-nth-node-from-end-of-list/

    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        ListNode front = null, back = null;
        while (n + 1 > 0) {
            if (front == null) {
                front = head;
            }
            front = front.next;
            n--;
        }
        if (front == null) {
            return head.next;
        }
        while (front != null) {
            front = front.next;
            if (back == null) {
                back = head;
            }
            else {
                back = back.next;
            }
        }
        back.next = back.next.next;
        return head;
    }

    // Letter Combinations of a Phone Number 
    // http://oj.leetcode.com/problems/letter-combinations-of-a-phone-number/
    public ArrayList<String> letterCombinations(String digits) {
        String[][] map = new String[][] { { "" }, { "" }, { "a", "b", "c" }, { "d", "e", "f" }, { "g", "h", "i" }, { "j", "k", "l" }, { "m", "n", "o" }, { "q", "p", "r" }, { "t", "u", "v" }, { "w", "x", "z", "y" } };
        return letterCombinationRecursion(digits, 0, map);
    }

    private ArrayList<String> letterCombinationRecursion(String digits, int index, String[][] map) {
        ArrayList<String> list = new ArrayList<>();
        if (index == digits.length()) {
            list.add(new String());
            return list;
        }
        for (String preStr : letterCombinationRecursion(digits, index + 1, map)) {
            for (String cur : map[digits.charAt(index)]) {
                list.add(cur + preStr);
            }
        }
        return list;
    }

    // Longest Common Prefix
    // http://oj.leetcode.com/problems/longest-common-prefix/
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return null;
        }
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            prefix = longestCommonPrefix(prefix, strs[i]);
        }
        return prefix;
    }

    private String longestCommonPrefix(String str1, String str2) {
        if (str1.isEmpty() || str2.isEmpty()) {
            return "";
        }
        StringBuilder str = new StringBuilder();
        int i = 0;
        while (i != str1.length() && i != str2.length() && str1.charAt(i) == str2.charAt(i)) {
            str.append(str1.charAt(i));
            i++;
        }
        return str.toString();
    }

    // ZigZag Conversion 
    // http://oj.leetcode.com/problems/zigzag-conversion/
    public String convert(String s, int nRows) {
        if (nRows == 1) {
            return s;
        }
        int n = s.length() / (2 * nRows - 2);
        StringBuilder res = new StringBuilder();
        if (s.length() % (2 * nRows - 2) > 0) {
            n++;
        }
        int base, first, second;
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < n; j++) {
                base = j * (2 * nRows - 2);
                first = base + i;
                if (first < s.length()) {
                    res.append(s.charAt(first));
                }
                second = base + 2 * (nRows - 1) - i;
                if (i != 0 & i != nRows - 1 && second < s.length()) {
                    res.append(s.charAt(second));
                }
            }
        }
        return res.toString();
    }

    // Reverse Integer 
    // http://oj.leetcode.com/problems/reverse-integer/
    public int reverse(int x) {
        int sign = x > 0 ? 1 : -1;
        x = Math.abs(x);
        int len = (int) Math.log10(x) + 1;
        int high, low;
        for (int i = 0; i < len / 2; i++) {
            low = getNthDigit(x, i);
            high = getNthDigit(x, len - 1 - i);
            x = x + (high - low) * (int) Math.pow(10, i) + (low - high) * (int) Math.pow(10, len - i - 1);
        }
        return sign * x;
    }

    private int getNthDigit(int x, int i) {
        return (x / (int) Math.pow(10, i)) % 10;
    }

    // String to Integer (atoi) 
    // http://oj.leetcode.com/problems/string-to-integer-atoi/
    // !!! Integer.MIN_VALUE = -2147483648 !!!
    // !!! Integer.MAX_VALUE = 2147483647 !!!
    public int atoi(String str) {
        str = str.trim();
        int sign = 1, i = 0;
        long res = 0;
        if (str.length() == 0) {
            return 0;
        }
        if (str.charAt(0) == '-') {
            sign = -1;
            i++;
        }
        else if (str.charAt(0) == '+') {
            sign = 1;
            i++;
        }
        for (; i < str.length(); i++) {
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                res = 10 * res + str.charAt(i) - 48;
                if (res * sign >= Integer.MAX_VALUE || res * sign < Integer.MIN_VALUE) {
                    return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                }
            }
            else {
                return (int) res * sign;
            }
        }
        return (int) res * sign;
    }

    // Palindrome Number 
    // http://oj.leetcode.com/problems/palindrome-number/
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        int len = (int) Math.log10(x) + 1;
        for (int i = 0; i < len / 2; i++) {
            if (getNthDigit(x, i) != getNthDigit(x, len - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    // Container With Most Water
    public int maxArea(int[] height) {
        int max = 0;
        for (int i = 0; i < height.length - 1; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int container = (j - i) * Math.min(height[i], height[j]);
                if (container > max) {
                    max = container;
                }
            }
        }
        return max;
    }

    // Best Time to Buy and Sell Stock 1
    public int maxProfit(int[] prices) {
        if (prices.length == 0 || prices.length == 1) {
            return 0;
        }
        int start = -1, end = -1, max = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i + 1] <= prices[i]) {
                continue;
            }
            else if (start == -1 || i > end) {
                int profit = prices[getMax(i, prices)] - prices[i];
                if (profit > max) {
                    start = i;
                    end = getMax(i, prices);
                    max = profit;
                }
            }
            else if (prices[i] < prices[start]) {
                start = i;
                max = prices[end] - prices[i];
            }
        }
        return max;
    }

    private int getMax(int start, int[] prices) {
        int[] range = Arrays.copyOfRange(prices, start, prices.length);
        int max = prices[start];
        int maxIndex = start;
        for (int i = 0; i < range.length; i++) {
            if (max < range[i]) {
                max = range[i];
                maxIndex = i + start;
            }
        }
        return maxIndex;
    }

    // Best Time to Buy and Sell Stock 2
    public int maxProfit2(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        int profit = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i] < prices[i + 1]) {
                profit += prices[i + 1] - prices[i];
            }
        }
        return profit;
    }

    // Best Time to Buy and Sell Stock 3
    public int maxProfit3(int[] prices) {
        int len = prices.length;
        if (len == 0 || len == 1) {
            return 0;
        }
        int start, end;
        ArrayList<Integer> profits = new ArrayList<>();
        int i = 0;
        while (i < len - 1) {
            start = getNextLowPoint(prices, i);
            end = getNextHighPoint(prices, start);
            profits.add(prices[end] - prices[start]);
            i = end;
        }
        if (profits.size() == 0) {
            return 0;
        }
        else if (profits.size() == 1) {
            return Collections.max(profits);
        }
        else {
            int max = Collections.max(profits);
            profits.remove(Collections.max(profits));
            max += Collections.max(profits);
            return max;
        }

    }

    private int getNextLowPoint(int[] prices, int start) {
        int i = start;
        while (i < prices.length - 1 && prices[i] >= prices[i + 1]) {
            i++;
        }
        return i;
    }

    private int getNextHighPoint(int[] prices, int start) {
        int i = start;
        while (i < prices.length - 1 && prices[i] <= prices[i + 1]) {
            i++;
        }
        return i;
    }

    // Triangle
    // http://oj.leetcode.com/problems/triangle/\
    public int minimumTotal(ArrayList<ArrayList<Integer>> triangle) {
        HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
        return minTotal(triangle, 0, 0, map);
    }

    private int minTotal(ArrayList<ArrayList<Integer>> triangle, int layer, int index, HashMap<Integer, HashMap<Integer, Integer>> map) {
        if (map.containsKey(layer) && map.get(layer).containsKey(index)) {
            return map.get(layer).get(index);
        }
        int current = triangle.get(layer).get(index);
        if (layer == triangle.size() - 1) {
            return current;
        }
        else {
            int leftMin = minTotal(triangle, layer + 1, index, map);
            int rightMin = minTotal(triangle, layer + 1, index + 1, map);
            int currentMin = Math.min(leftMin, rightMin) + current;
            if (!map.containsKey(layer)) {
                map.put(layer, new HashMap<Integer, Integer>());
            }
            map.get(layer).put(index, currentMin);
            return currentMin;
        }
    }

    // Pascal's Triangle
    // http://oj.leetcode.com/problems/pascals-triangle/
    public ArrayList<ArrayList<Integer>> generate(int numRows) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<Integer>();
        ArrayList<Integer> nextList;
        for (int i = 0; i < numRows; i++) {
            nextList = getNextLayer(list);
            lists.add(nextList);
            list = nextList;
        }
        return lists;
    }

    private ArrayList<Integer> getNextLayer(ArrayList<Integer> list) {
        ArrayList<Integer> nextList = new ArrayList<>();
        Iterator<Integer> iterator = list.iterator();
        int leftIterator = 0;
        while (iterator.hasNext()) {
            int current = iterator.next();
            nextList.add(leftIterator + current);
            leftIterator = current;
        }
        nextList.add(1);
        return nextList;
    }

    // Populating Next Right Pointers in Each Node
    // http://oj.leetcode.com/problems/populating-next-right-pointers-in-each-node/
    public void connect(TreeLinkNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeLinkNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            TreeLinkNode rightNode = null;
            TreeLinkNode currentNode;
            for (int i = 0; i < size; i++) {
                currentNode = queue.poll();
                currentNode.next = rightNode;
                if (currentNode.right != null) {
                    queue.add(currentNode.right);
                }
                if (currentNode.left != null) {
                    queue.add(currentNode.left);
                }
                rightNode = currentNode;
            }
        }
    }

    // Distinct Subsequences 
    // http://oj.leetcode.com/problems/distinct-subsequences/
    public int numDistinct(String s, String t) {
        if (s.equals(t)) {
            return 0;
        }
        else if (s.length() == 0 || t.length() == 0) {
            return 0;
        }
        int changes = 0;
        int ss = 0, tt = 0;
        while (ss != s.length() && tt != t.length()) {
            if (s.charAt(ss) == t.charAt(tt)) {
                ss++;
                tt++;
            }
            else {
                ss++;
                tt++;
            }

        }
        if (ss == s.length() && tt == t.length()) {
            return changes;
        }
        return 0;
    }

    // Path Sum 
    // http://oj.leetcode.com/problems/path-sum/
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        return hasPathSumRecursion(root, sum);
    }

    private boolean hasPathSumRecursion(TreeNode root, int sum) {
        if (root == null) {
            return sum == 0;
        }
        else if (root.left == null && root.right == null) {
            return root.val == sum;
        }
        else if (root.left == null && root.right != null) {
            return hasPathSumRecursion(root.right, sum - root.val);
        }
        else if (root.left != null && root.right == null) {
            return hasPathSumRecursion(root.left, sum - root.val);
        }
        else {
            return hasPathSumRecursion(root.right, sum - root.val) || hasPathSumRecursion(root.left, sum - root.val);
        }
    }

    // Minimum Depth of Binary Tree
    // http://oj.leetcode.com/problems/minimum-depth-of-binary-tree/
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int layer = 1;
        TreeNode currentNode;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                currentNode = queue.poll();
                if (currentNode.left == null && currentNode.right == null) {
                    return layer;
                }
                if (currentNode.left != null) {
                    queue.add(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.add(currentNode.right);
                }
            }
            layer++;
        }
        return layer;
    }

    // Balanced Binary Tree 
    // http://oj.leetcode.com/problems/balanced-binary-tree/
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        HashMap<TreeNode, Integer> heights = new HashMap<>();
        return checkHeight(root, heights);
    }

    private boolean checkHeight(TreeNode node, HashMap<TreeNode, Integer> heights) {
        if (node == null) {
            return true;
        }
        int left = getHeight(node.left, heights);
        int right = getHeight(node.right, heights);
        if (Math.abs(left - right) > 1) {
            return false;
        }
        return checkHeight(node.left, heights) && checkHeight(node.right, heights);
    }

    private int getHeight(TreeNode node, HashMap<TreeNode, Integer> heights) {
        if (node == null) {
            return 0;
        }
        else if (heights.containsKey(node)) {
            return heights.get(node);
        }
        else {
            int height = 1 + Math.max(getHeight(node.left, heights), getHeight(node.right, heights));
            heights.put(node, height);
            return height;
        }
    }

    // Convert Sorted List to Binary Search Tree
    // http://oj.leetcode.com/problems/convert-sorted-list-to-binary-search-tree/
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {
            return null;
        }
        TreeNode root = new TreeNode(-1);
        BSTRecursion(head, root);
        return root;
    }

    private void BSTRecursion(ListNode head, TreeNode root) {
        ListNode mid = getMidNode(head);
        root.val = mid.val;
        if (head != mid) {
            // !!! Cannot change the object the reference points to in a sub method !!!
            // !!! Can only change the object's filed !!!
            // !!! So must initiate before passing in, and must root.left it won't be null !!!
            root.left = new TreeNode(-1);
            BSTRecursion(head, root.left);
        }
        if (mid.next != null) {
            root.right = new TreeNode(-1);
            BSTRecursion(mid.next, root.right);
        }
    }

    private ListNode getMidNode(ListNode node) {
        if (node == null) {
            return null;
        }
        ListNode fast = node, slow = node, slowParent = null;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slowParent = slow;
            slow = slow.next;

        }
        if (slowParent != null) {
            slowParent.next = null;
        }
        return slow;
    }

    // Convert Sorted Array to Binary Search Tree
    // http://oj.leetcode.com/problems/convert-sorted-array-to-binary-search-tree/
    public TreeNode sortedArrayToBST(int[] num) {
        if (num.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(-1);
        sortedArrayBSTRecursion(num, 0, num.length - 1, root);
        return root;
    }

    private void sortedArrayBSTRecursion(int[] num, int start, int end, TreeNode root) {
        int mid = (start + end) / 2;
        root.val = num[mid];
        if (mid > start) {
            root.left = new TreeNode(-1);
            sortedArrayBSTRecursion(num, start, mid - 1, root.left);
        }
        if (end > mid) {
            root.right = new TreeNode(-1);
            sortedArrayBSTRecursion(num, mid + 1, end, root.right);
        }
    }

    // Binary Tree Level Order Traversal II
    // http://oj.leetcode.com/problems/binary-tree-level-order-traversal-ii/
    public ArrayList<ArrayList<Integer>> levelOrderBottom(TreeNode root) {
        Stack<ArrayList<Integer>> stack = new Stack<>();
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        if (root == null) {
            return lists;
        }
        LinkedList<TreeNode> list = new LinkedList<>();
        list.add(root);
        while (!list.isEmpty()) {
            stack.push(linkedList2ArrayList(list));
            int size = list.size();
            for (int i = 0; i < size; i++) {
                TreeNode current = list.pollFirst();
                if (current.left != null) {
                    list.addLast(current.left);
                }
                if (current.right != null) {
                    list.addLast(current.right);
                }
            }
        }
        while (!stack.isEmpty()) {
            lists.add(stack.pop());
        }
        return lists;
    }

    private ArrayList<Integer> linkedList2ArrayList(LinkedList<TreeNode> linkedList) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        Iterator<TreeNode> iterator = linkedList.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().val);
        }
        return arrayList;
    }

    // Binary Tree Level Order Traversal 
    // http://oj.leetcode.com/problems/binary-tree-level-order-traversal/
    public ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        if (root == null) {
            return lists;
        }
        LinkedList<TreeNode> list = new LinkedList<>();
        list.add(root);
        while (!list.isEmpty()) {
            lists.add(linkedList2ArrayList(list));
            int size = list.size();
            for (int i = 0; i < size; i++) {
                TreeNode current = list.pollFirst();
                if (current.left != null) {
                    list.addLast(current.left);
                }
                if (current.right != null) {
                    list.addLast(current.right);
                }
            }
        }
        return lists;
    }

    // Construct Binary Tree from Inorder and Postorder Traversal 
    // http://oj.leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (postorder.length != inorder.length || postorder.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(-1);
        constructTreeFromTraversal(inorder, postorder, root);
        return root;
    }

    private void constructTreeFromTraversal(int[] inorder, int[] postorder, TreeNode root) {
        int rootVal = postorder[postorder.length - 1];
        root.val = rootVal;
        int rootIndex = findValueInArray(inorder, rootVal);
        if (rootIndex != 0) {
            root.left = new TreeNode(-1);
            constructTreeFromTraversal(
                    Arrays.copyOfRange(inorder, 0, rootIndex),
                    Arrays.copyOfRange(postorder, 0, rootIndex),
                    root.left);
        }
        if (rootIndex < inorder.length - 1) {
            root.right = new TreeNode(-1);
            constructTreeFromTraversal(
                    Arrays.copyOfRange(inorder, rootIndex + 1, inorder.length),
                    Arrays.copyOfRange(postorder, rootIndex, postorder.length - 1),
                    root.right);
        }
    }

    private int findValueInArray(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // Construct Binary Tree from Preorder and Inorder Traversal 
    // http://oj.leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
    public TreeNode buildTree2(int[] preorder, int[] inorder) {
        if (preorder.length != inorder.length || preorder.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(-1);
        constructTreeFromTraversal2(preorder, inorder, root);
        return root;
    }

    private void constructTreeFromTraversal2(int[] preorder, int[] inorder, TreeNode root) {
        int rootVal = preorder[0];
        root.val = rootVal;
        int rootIndex = findValueInArray(inorder, rootVal);
        if (rootIndex != 0) {
            root.left = new TreeNode(-1);
            constructTreeFromTraversal2(
                    Arrays.copyOfRange(preorder, 1, rootIndex + 1),
                    Arrays.copyOfRange(inorder, 0, rootIndex),
                    root.left);
        }
        if (rootIndex < inorder.length - 1) {
            root.right = new TreeNode(-1);
            constructTreeFromTraversal2(
                    Arrays.copyOfRange(preorder, rootIndex + 1, preorder.length),
                    Arrays.copyOfRange(inorder, rootIndex + 1, inorder.length),
                    root.right);
        }
    }

    // Maximum Depth of Binary Tree 
    // http://oj.leetcode.com/problems/maximum-depth-of-binary-tree/
    // !!! Breadth first search is good at finding closest node !!!
    // !!! It also works good at finding deepest node !!!
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int layer = 0;
        TreeNode currentNode;
        while (!queue.isEmpty()) {
            layer++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                currentNode = queue.poll();
                if (currentNode.left != null) {
                    queue.add(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.add(currentNode.right);
                }
            }
        }
        return layer;
    }

    // Binary Tree Zigzag Level Order Traversal
    public ArrayList<ArrayList<Integer>> zigzagLevelOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        recursionDFS(root, 0, lists);
        for (int i = 0; i < lists.size(); i++) {
            if (i % 2 == 1) {
                Collections.reverse(lists.get(i));
            }
        }
        return lists;
    }

    // !!! Use DFS is not hard on this obvious BFS question !!!
    public void recursionDFS(TreeNode node, int depth, ArrayList<ArrayList<Integer>> lists) {
        if (node == null) {
            return;
        }
        if (depth < lists.size()) {
            lists.get(depth).add(node.val);
        }
        else {
            ArrayList<Integer> list = new ArrayList<>();
            lists.add(list);
            list.add(node.val);
        }
        recursionDFS(node.left, depth + 1, lists);
        recursionDFS(node.right, depth + 1, lists);
    }

    // Symmetric Tree 
    // http://oj.leetcode.com/problems/symmetric-tree/
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetricRecursion(root.left, root.right);
    }

    private boolean isSymmetricRecursion(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        else if ((left == null && right != null) || (left != null && right == null)) {
            return false;
        }
        else if (left.val != right.val) {
            return false;
        }
        else {
            return isSymmetricRecursion(left.left, right.right) && isSymmetricRecursion(left.right, right.left);
        }
    }

    // Same Tree
    // http://oj.leetcode.com/problems/same-tree/
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        else if ((p == null && q != null) || (p != null && q == null)) {
            return false;
        }
        else if (p.val != q.val) {
            return false;
        }
        else {
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        }
    }

    // Recover Binary Search Tree
    // http://oj.leetcode.com/problems/recover-binary-search-tree/
    public void recoverTree(TreeNode root) {

    }

    // Validate Binary Search Tree
    // http://oj.leetcode.com/problems/validate-binary-search-tree/
    public boolean isValidBST(TreeNode root) {
        return isValidBSTRecursion(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isValidBSTRecursion(TreeNode node, int min, int max) {
        if (node == null) {
            return true;
        }
        else if (node.val < min || node.val > max) {
            return false;
        }
        else {
            return isValidBSTRecursion(node.left, min, node.val) || isValidBSTRecursion(node.right, node.val, max);
        }
    }

    // Interleaving String
    // http://oj.leetcode.com/problems/interleaving-string/
    public boolean isInterleave(String s1, String s2, String s3) {
        HashMap<String, Boolean> map = new HashMap<>();
        return isInterleaveRecursion(s1, s2, s3, map);
    }

    private boolean isInterleaveRecursion(String s1, String s2, String s3, HashMap<String, Boolean> map) {
        String key = s1 + ":" + s2 + ":" + s3;
        if (s1.isEmpty() && s2.isEmpty() && s3.isEmpty()) {
            return true;
        }
        else if (s1.isEmpty()) {
            return s2.equals(s3);
        }
        else if (s2.isEmpty()) {
            return s1.equals(s3);
        }
        else if (map.containsKey(key)) {
            return map.get(key);
        }
        else if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        boolean result;
        if (s1.charAt(0) != s3.charAt(0)) {
            result = isInterleaveRecursion(s1, s2.substring(1), s3.substring(1), map);
        }
        else if (s2.charAt(0) != s3.charAt(0)) {
            result = isInterleaveRecursion(s1.substring(1), s2, s3.substring(1), map);
        }
        else {
            result = isInterleaveRecursion(s1, s2.substring(1), s3.substring(1), map) || isInterleaveRecursion(s1.substring(1), s2, s3.substring(1), map);
        }
        map.put(key, result);
        return result;
    }

    // Unique Binary Search Trees
    // http://oj.leetcode.com/problems/unique-binary-search-trees/
    public int numTrees(int n) {
        if (n == 0) {
            return 0;
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        return numTreesRecursion(n, map);
    }

    public int numTreesRecursion(int n, HashMap<Integer, Integer> map) {
        if (n == 0 || n == 1) {
            return 1;
        }
        if (map.containsKey(n)) {
            return map.get(n);
        }
        int total = 0;
        for (int i = 1; i <= n; i++) {
            total += numTreesRecursion(i - 1, map) * numTreesRecursion(n - i, map);
        }
        map.put(n, total);
        return total;
    }

    // Binary Tree Inorder Traversal 
    // http://oj.leetcode.com/problems/binary-tree-inorder-traversal/
    public ArrayList<Integer> inorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        ArrayList<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        stack.push(root);
        TreeNode previous = null, current = null;
        while (!stack.isEmpty()) {
            current = stack.pop();
            // if previous is current's parent
            if (previous == null || previous.left == current || previous.right == current) {
                if (current.right != null) {
                    stack.push(current.right);
                }
                if (current.left == null) {
                    result.add(current.val);
                }
                else {
                    stack.push(current);
                    stack.push(current.left);
                }
            }
            // !!! if previous isn't current's parent !!!
            // !!! Don't check current.left == previous !!!
            else {
                result.add(current.val);
            }
            previous = current;
        }
        return result;
    }

    // Restore IP Addresses
    public ArrayList<String> restoreIpAddresses(String s) {
        ArrayList<String> lists = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        restoreIpAdresses(s, 4, list, lists);
        return lists;
    }

    private void restoreIpAdresses(String s, int num, ArrayList<Integer> list, ArrayList<String> lists) {
        if (num == 1) {
            if (isValidIPElement(s)) {
                list.add(Integer.valueOf(s));
                lists.add(IPArray2String(list));
                list.remove(list.size() - 1);
                return;
            }
        }
        if (s.length() < num || s.length() > num * 3) {
            return;
        }
        // 1 digit
        int element = Integer.valueOf(s.substring(0, 1));
        list.add(element);
        restoreIpAdresses(s.substring(1), num - 1, list, lists);
        list.remove(list.size() - 1);
        // !!! Very easy to miss !!!
        if (element == 0) {
            return;
        }
        // 2 digit
        if (s.length() >= num + 1) {
            element = Integer.valueOf(s.substring(0, 2));
            list.add(element);
            restoreIpAdresses(s.substring(2), num - 1, list, lists);
            list.remove(list.size() - 1);
        }
        // 3 digit
        if (s.length() >= num + 2) {
            if (isValidIPElement(s.substring(0, 3))) {
                element = Integer.valueOf(s.substring(0, 3));
                list.add(element);
                restoreIpAdresses(s.substring(3), num - 1, list, lists);
                list.remove(list.size() - 1);
            }
        }
    }

    private boolean isValidIPElement(String s) {
        if (s.charAt(0) == '0') {
            return s.length() == 1;
        }
        int n = Integer.valueOf(s);
        return n >= 0 && n <= 255;
    }

    private String IPArray2String(ArrayList<Integer> list) {
        String res = new String();
        for (int i = 0; i < list.size(); i++) {
            res += String.valueOf(list.get(i));
            if (i != list.size() - 1) {
                res += ".";
            }
        }
        return res;
    }

    // Decode Ways
    // http://oj.leetcode.com/problems/decode-ways/
    public int numDecodings(String s) {
        if (s.length() == 0) {
            return 0;
        }
        HashMap<String, Integer> map = new HashMap<>();
        return numDecoding(s, map);
    }

    private int numDecoding(String s, HashMap<String, Integer> map) {
        if (s.length() == 0 || (s.length() == 1 && Integer.valueOf(s) != 0)) {
            return 1;
        }
        if (map.containsKey(s)) {
            return map.get(s);
        }
        // !!! Integer.valueOf('0') = 48 !!!
        // !!! Integer.valueOf("0") = 0 !!!
        int first = Integer.valueOf(s.substring(0, 1));
        if (first == 0) {
            return 0;
        }
        else {
            int total = 0;
            if (Integer.valueOf(s.substring(0, 2)) <= 26) {
                total += numDecoding(s.substring(2), map);
            }
            total += numDecoding(s.substring(1), map);
            map.put(s, total);
            return total;
        }
    }

    // Merge Sorted Array
    // http://oj.leetcode.com/problems/merge-sorted-array/
    public void merge(int A[], int m, int B[], int n) {
        int p = A.length - 1;
        while (n > 0 && m > 0) {
            if (A[m - 1] >= B[n - 1]) {
                A[p] = A[m - 1];
                m--;
            }
            else {
                A[p] = B[n - 1];
                n--;
            }
            p--;
        }
        while (n > 0) {
            A[p] = B[n - 1];
            n--;
            p--;
        }
    }

    // Partition List 
    // http://oj.leetcode.com/problems/partition-list/
    public ListNode partition(ListNode head, int x) {
        if (head == null) {
            return null;
        }
        ListNode smallHead = new ListNode(-1), bigHead = new ListNode(-1), iterator = head;
        ListNode smallIterator = smallHead, bigIterator = bigHead;
        while (iterator != null) {
            if (iterator.val < x) {
                smallIterator.next = new ListNode(iterator.val);
                smallIterator = smallIterator.next;
            }
            else {
                bigIterator.next = new ListNode(iterator.val);
                bigIterator = bigIterator.next;
            }
            iterator = iterator.next;
        }
        smallIterator.next = bigHead.next;
        return smallHead.next;
    }

    // Maximal Rectangle 
    // http://oj.leetcode.com/problems/maximal-rectangle/
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        // if only one row or column, return 1 if any 1 exists
        // Store in ArrayList for sorting later
        ArrayList<Integer> row = new ArrayList<>();
        row.add(-1);
        row.add(matrix.length);
        ArrayList<Integer> column = new ArrayList<>();
        column.add(-1);
        column.add(matrix[0].length);
        for (int n = 0; n < matrix.length; n++) {
            for (int m = 0; m < matrix[0].length; m++) {
                if (matrix[n][m] == '0') {
                    row.add(n);
                    column.add(m);
                }
            }
        }
        int rowMax = getMaxGap(row);
        int colMax = getMaxGap(column);
        return rowMax * colMax;

    }

    int getMaxGap(ArrayList<Integer> list) {
        Collections.sort(list);
        int gap = 0, previous = list.get(0);
        for (Integer i : list) {
            gap = gap > (i - previous - 1) ? gap : (i - previous - 1);
            previous = i;
        }
        return gap;
    }

    // Largest Rectangle in Histogram
    // http://oj.leetcode.com/problems/largest-rectangle-in-histogram/
    public int largestRectangleArea(int[] height) {
        HashSet<Integer> set = new HashSet<>();
        int max = 0;
        for (int i = 0; i < height.length; i++) {
            if (set.contains(i)) {
                continue;
            }
            int area = findWidth(height, i, set) * height[i];
            max = max > area ? max : area;
        }
        return max;
    }

    int findWidth(int[] height, int n, HashSet<Integer> set) {
        int left = n, right = n + 1;
        while (right < height.length && height[right] >= height[n]) {
            if (height[right] == height[n]) {
                set.add(right);
            }
            right++;
        }
        return right - left;
    }

    // Remove Duplicates from Sorted List 
    // http://oj.leetcode.com/problems/remove-duplicates-from-sorted-list/
    public ListNode deleteDuplicates1(ListNode head) {
        HashSet<Integer> set = new HashSet<>();
        ListNode iterator = head, parent = null;
        while (iterator != null) {
            if (set.contains(iterator.val)) {
                parent.next = iterator.next;
                iterator = iterator.next;
            }
            else {
                set.add(iterator.val);
                parent = iterator;
                iterator = iterator.next;
            }
        }
        return head;
    }

    // Remove Duplicates from Sorted List II 
    // http://oj.leetcode.com/problems/remove-duplicates-from-sorted-list-ii/
    public ListNode deleteDuplicates(ListNode head) {
        HashMap<Integer, Integer> map = new HashMap<>();
        ListNode iterator = head;
        while (iterator != null) {
            if (map.containsKey(iterator.val)) {
                map.put(iterator.val, map.get(iterator.val) + 1);
            }
            else {
                map.put(iterator.val, 1);
            }
            iterator = iterator.next;
        }
        iterator = head;
        ListNode parent = new ListNode(-1);
        parent.next = head;
        ListNode parentIterator = parent;
        while (iterator != null) {
            if (map.get(iterator.val) > 1) {
                parentIterator.next = iterator.next;
                iterator = iterator.next;
            }
            else {
                parentIterator = iterator;
                iterator = iterator.next;
            }
        }
        return parent.next;
    }

    // Word Search
    // http://oj.leetcode.com/problems/word-search/
    public boolean exist(char[][] board, String word) {
        // !!! Remember to track the visited points !!!
        // !!! Instead of using nested hashmap, boolean matrix is easy !!!
        boolean[][] visited = new boolean[board.length][board[0].length];
        for (boolean[] booleanRow : visited) {
            Arrays.fill(booleanRow, false);
        }
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] == word.charAt(0)) {
                    visited[x][y] = true;
                    if (exist(board, word.substring(1), x, y, visited)) {
                        return true;
                    }
                    visited[x][y] = false;
                }
            }
        }
        return false;
    }

    private boolean exist(char[][] board, String word, int x, int y, boolean[][] visited) {
        if (word.isEmpty()) {
            return true;
        }
        int boardx = board.length - 1;
        int boardy = board[0].length - 1;
        // !!! Check x is in the range before using it in the array !!!
        if (x < boardx && !visited[x + 1][y] && board[x + 1][y] == word.charAt(0)) {
            visited[x + 1][y] = true;
            if (exist(board, word.substring(1), x + 1, y, visited)) {
                return true;
            }
            visited[x + 1][y] = false;
        }
        if (x > 0 && !visited[x - 1][y] && board[x - 1][y] == word.charAt(0)) {
            visited[x - 1][y] = true;
            if (exist(board, word.substring(1), x - 1, y, visited)) {
                return true;
            }
            visited[x - 1][y] = false;
        }
        if (y < boardy && !visited[x][y + 1] && board[x][y + 1] == word.charAt(0)) {
            visited[x][y + 1] = true;
            if (exist(board, word.substring(1), x, y + 1, visited)) {
                return true;
            }
            visited[x][y + 1] = false;
        }
        if (y > 0 && !visited[x][y - 1] && board[x][y - 1] == word.charAt(0)) {
            visited[x][y - 1] = true;
            if (exist(board, word.substring(1), x, y - 1, visited)) {
                return true;
            }
            visited[x][y - 1] = false;
        }
        return false;
    }

    // Subsets
    // http://oj.leetcode.com/problems/subsets/
    public ArrayList<ArrayList<Integer>> subsets(int[] S) {
        ArrayList<ArrayList<Integer>> lists = subsets(S, 0);
        for (ArrayList<Integer> list : lists) {
            Collections.sort(list);
        }
        return lists;
    }

    private ArrayList<ArrayList<Integer>> subsets(int[] S, int index) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        if (index == S.length) {
            lists.add(new ArrayList<Integer>());
            return lists;
        }
        ArrayList<ArrayList<Integer>> nextLists = subsets(S, index + 1);
        lists.addAll(nextLists);
        for (ArrayList<Integer> list : nextLists) {
            ArrayList<Integer> newList = (ArrayList<Integer>) list.clone();
            newList.add(S[index]);
            lists.add(newList);
        }
        return lists;
    }

    // Combinations 
    // http://oj.leetcode.com/problems/combinations/
    public ArrayList<ArrayList<Integer>> combine(int n, int k) {
        return combine(n, k, k);
    }

    public ArrayList<ArrayList<Integer>> combine(int n, int k, int total) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        if (k == 0) {
            lists.add(new ArrayList<Integer>());
            return lists;
        }
        ArrayList<ArrayList<Integer>> oldLists = combine(n, k - 1, total);
        for (ArrayList<Integer> list : oldLists) {
            // !!! Important to figure out 'for' loop's range !!!
            for (int i = list.isEmpty() ? 1 : (list.get(list.size() - 1) + 1); i < n + 1 - (total - k); i++) {
                ArrayList<Integer> newList = (ArrayList<Integer>) list.clone();
                newList.add(i);
                lists.add(newList);
            }
        }
        return lists;
    }

    // Sort Colors 
    // http://oj.leetcode.com/problems/sort-colors/
    public void sortColors(int[] A) {
        int left = quickSort(A, 0, 0, A.length - 1);
        quickSort(A, 1, left, A.length - 1);
    }

    private int quickSort(int[] A, int pivot, int start, int end) {
        int left = start;
        int right = end;
        while (left < right) {
            while (left < right && A[left] == pivot) {
                left++;
            }
            while (right > left && A[right] != pivot) {
                right--;
            }
            if (left != right) {
                A[right] = A[left];
                A[left] = pivot;
            }
        }
        return left;
    }

    // Search a 2D Matrix
    // http://oj.leetcode.com/problems/search-a-2d-matrix/
    // !!! This simple question takes me so much time !!!
    // !!! Think through the value of i in the first 'for' loop
    public boolean searchMatrix(int[][] matrix, int target) {
        int i = 0;
        for (i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == target) {
                return true;
            }
            else if (matrix[i][0] > target) {
                if (i == 0) {
                    return false;
                }
                i--;
                break;
            }
        }
        if (i == matrix.length) {
            i--;
        }
        for (int l = 0; l < matrix[i].length; l++) {
            if (matrix[i][l] == target) {
                return true;
            }
        }
        return false;
    }

    // Set Matrix Zeroes 
    // http://oj.leetcode.com/problems/set-matrix-zeroes/
    public void setZeroes(int[][] matrix) {
        HashSet<Integer> row = new HashSet<>();
        HashSet<Integer> column = new HashSet<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    row.add(i);
                    column.add(j);
                }
            }
        }
        Iterator<Integer> iterator = row.iterator();
        while (iterator.hasNext()) {
            int rowNum = iterator.next();
            for (int i = 0; i < matrix[0].length; i++) {
                matrix[rowNum][i] = 0;
            }
        }
        iterator = column.iterator();
        while (iterator.hasNext()) {
            int colNum = iterator.next();
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][colNum] = 0;
            }
        }
    }

    // Simplify Path
    // http://oj.leetcode.com/problems/simplify-path/
    public String simplifyPath(String path) {
        LinkedList<String> result = new LinkedList<String>();
        for (String str : path.split("/")) {
            if (str.equals(".")) {
                continue;
            }
            else if (str.equals("..")) {
                if (result.size() > 0) {
                    result.removeLast();
                }
            }
            // !!! Note, String.split generates some empty strings !!!
            else if (!str.isEmpty()) {
                result.addLast(str);
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append("/");
        Iterator<String> iterator = result.iterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
            if (iterator.hasNext()) {
                builder.append("/");
            }
        }
        return builder.toString();
    }

    // Climbing Stairs 
    // http://oj.leetcode.com/problems/climbing-stairs/
    public int climbStairs(int n) {
        HashMap<Integer, Integer> map = new HashMap<>(n);
        return climbStairsRecursion(n, map);
    }

    private int climbStairsRecursion(int n, HashMap<Integer, Integer> map) {
        if (map.containsKey(n)) {
            return map.get(n);
        }
        else if (n == 1) {
            return 1;
        }
        else if (n == 2) {
            return 2;
        }
        else {
            int result = climbStairsRecursion(n - 1, map) + climbStairsRecursion(n - 2, map);
            map.put(n, result);
            return result;
        }
    }

    // Sqrt(x) 
    // http://oj.leetcode.com/problems/sqrtx/
    public int sqrt(int x) {
        if (x < 0) {
            return -1;
        }
        else if (x == 1) {
            return 1;
        }
        int top = x / 2 + 1;
        int bottom = 0;
        int i;
        double square;
        while (top - bottom > 1) {
            i = (top + bottom) / 2;
            // !!! Must cast to long before the operation, not after!!!
            square = (long) i * (long) i;
            if (square > x) {
                top = i;
            }
            else if (square < x) {
                bottom = i;
            }
            else {
                return i;
            }

        }
        return (top + bottom) / 2;
    }

    // Plus One
    // http://oj.leetcode.com/problems/plus-one/
    public int[] plusOne(int[] digits) {
        int carry = 1;
        int sum;
        for (int i = digits.length - 1; i >= 0; i--) {
            sum = digits[i] + carry;
            digits[i] = sum % 10;
            carry = (sum - digits[i]) / 10;
        }
        if (carry == 1) {
            int[] result = new int[digits.length + 1];
            result[0] = 1;
            for (int i = 0; i < digits.length; i++) {
                result[i + 1] = digits[i];
            }
            return result;
        }
        return digits;
    }

    // Add Binary 
    // http://oj.leetcode.com/problems/add-binary/
    public String addBinary(String a, String b) {
        ArrayList<String> list = new ArrayList<>();
        int n = Math.max(a.length(), b.length());
        int carry = 0;
        for (int i = 0; i < n; i++) {
            int num1 = i < a.length() ? Integer.valueOf(a.substring(a.length() - 1 - i, a.length() - i)) : 0;
            int num2 = i < b.length() ? Integer.valueOf(b.substring(b.length() - 1 - i, b.length() - i)) : 0;
            int sum = num1 + num2 + carry;
            list.add(String.valueOf(sum & 1));
            carry = sum >> 1;
        }
        if (carry != 0) {
            list.add(String.valueOf(carry));
        }
        Collections.reverse(list);
        String result = new String();
        for (String str : list) {
            result += str;
        }
        return result;
    }

    // Valid Number 
    // http://oj.leetcode.com/problems/valid-number/
    public boolean isNumber(String s) {
        return false;
    }

    // Merge Two Sorted Lists 
    // http://oj.leetcode.com/problems/merge-two-sorted-lists/
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode head = new ListNode(-1);
        ListNode result = head;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                head.next = l1;
                l1 = l1.next;
            }
            else {
                head.next = l2;
                l2 = l2.next;
            }
            head = head.next;
        }
        if (l1 != null) {
            head.next = l1;
        }
        if (l2 != null) {
            head.next = l2;
        }
        return result.next;
    }

    // Minimum Path Sum
    // http://oj.leetcode.com/problems/minimum-path-sum/
    public int minPathSum(int[][] grid) {
        int[][] sums = new int[grid.length][grid[0].length];
        for (int[] array : sums) {
            Arrays.fill(array, Integer.MIN_VALUE);
        }
        return minPathSum(grid, grid.length - 1, grid[0].length - 1, sums);
    }

    private int minPathSum(int[][] grid, int row, int col, int[][] sums) {
        if (sums[row][col] != Integer.MIN_VALUE) {
            return sums[row][col];
        }
        if (row == 0 && col == 0) {
            return grid[0][0];
        }
        int result = 0;
        if (row == 0 && col > 0) {
            result = minPathSum(grid, row, col - 1, sums) + grid[row][col];
        }
        else if (row > 0 && col == 0) {
            result = minPathSum(grid, row - 1, col, sums) + grid[row][col];
        }
        else {
            int down = minPathSum(grid, row - 1, col, sums);
            int right = minPathSum(grid, row, col - 1, sums);
            result = Math.min(down, right) + grid[row][col];
        }
        sums[row][col] = result;
        return result;
    }

    // Unique Paths
    // http://oj.leetcode.com/problems/unique-paths/
    // !!! Remember minus 1 from the length !!!
    // !!! Same mistake twice in a row !!!
    public int uniquePaths(int m, int n) {
        int[][] ways = new int[m][n];
        for (int[] row : ways) {
            Arrays.fill(row, -1);
        }
        return uniquePaths(m - 1, n - 1, ways);
    }

    private int uniquePaths(int m, int n, int[][] ways) {
        if (m == 0 && n == 0) {
            return 1;
        }
        else if (ways[m][n] != -1) {
            return ways[m][n];
        }
        else if (m == 0 || n == 0) {
            return 1;
        }
        else {
            int result = uniquePaths(m - 1, n, ways) + uniquePaths(m, n - 1, ways);
            ways[m][n] = result;
            return result;
        }
    }

    // Unique Paths II
    // http://oj.leetcode.com/problems/unique-paths-ii/
    // !!! So many details to miss !!!
    // !!! Check current point==1, check if m==0&&n==0 !!!
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] ways = new int[m][n];
        for (int[] row : ways) {
            Arrays.fill(row, -1);
        }
        return uniquePaths(obstacleGrid, m - 1, n - 1, ways);
    }

    private int uniquePaths(int[][] obstacleGrid, int m, int n, int[][] ways) {
        if (obstacleGrid[m][n] == 1) {
            return 0;
        }
        else if (m == 0 && n == 0) {
            return obstacleGrid[m][n] == 1 ? 0 : 1;
        }
        else if (ways[m][n] != -1) {
            return ways[m][n];
        }
        else {
            int result = 0;
            if (m > 0 && obstacleGrid[m - 1][n] != 1) {
                result += uniquePaths(obstacleGrid, m - 1, n, ways);
            }
            if (n > 0 && obstacleGrid[m][n - 1] != 1) {
                result += uniquePaths(obstacleGrid, m, n - 1, ways);
            }
            ways[m][n] = result;
            return result;
        }
    }

    // Rotate List 
    // http://oj.leetcode.com/problems/rotate-list/
    public ListNode rotateRight(ListNode head, int n) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode runner = head;
        int count = 1;
        while (runner.next != null) {
            runner = runner.next;
            count++;
        }
        runner.next = head;
        int times = count - (n % count);
        while (times > 0) {
            runner = runner.next;
            times--;
        }
        head = runner.next;
        runner.next = null;
        return head;
    }

    // Permutation Sequence 
    // http://oj.leetcode.com/problems/permutation-sequence/
    public String getPermutation(int n, int k) {
        LinkedList<String> numbers = new LinkedList<String>();
        for (int i = 1; i <= n; i++) {
            numbers.add(String.valueOf(i));
        }
        StringBuilder builder = new StringBuilder();
        getPermutationRecursion(n, numbers, builder, k);
        return builder.toString();
    }

    private void getPermutationRecursion(int n, LinkedList<String> numbers, StringBuilder builder, int k) {
        if (n == 1) {
            builder.append(numbers.get(0));
            return;
        }
        int previous = getExclamation(n - 1);
        // !!! I made a mistake here !!!
        // !!! Residual is the next k !!!
        int residual = k % previous;
        int index = k / previous - (residual == 0 ? 1 : 0);
        residual = residual == 0 ? previous : residual;
        String number = numbers.get(index);
        builder.append(number);
        numbers.remove(index);
        getPermutationRecursion(n - 1, numbers, builder, residual);
    }

    private int getExclamation(int n) {
        if (n < 0) {
            return -1;
        }
        else if (n == 0) {
            return 0;
        }
        int result = 1;
        while (n > 0) {
            result *= n;
            n--;
        }
        return result;
    }

    // Spiral Matrix II
    // http://oj.leetcode.com/problems/spiral-matrix-ii/
    public int[][] generateMatrix(int n) {
        int layers = n / 2;
        int num = 1, min, max, x, y;
        int[][] matrix = new int[n][n];
        for (int l = 0; l < layers; l++) {
            min = l;
            max = n - 1 - l;
            x = min;
            // !!! This assumes max>min !!!
            // !!! It doesn't work on the 1 element layer !!!
            for (y = min; y <= max - 1; y++) {
                matrix[x][y] = num++;
            }
            y = max;
            for (x = min; x <= max - 1; x++) {
                matrix[x][y] = num++;
            }
            x = max;
            for (y = max; y >= min + 1; y--) {
                matrix[x][y] = num++;
            }
            y = min;
            for (x = max; x >= min + 1; x--) {
                matrix[x][y] = num++;
            }
        }
        // !!! Consider the 1 element layer seperately !!!
        if (n % 2 == 1) {
            matrix[n / 2][n / 2] = num;
        }
        return matrix;
    }

    // Length of Last Word
    // http://oj.leetcode.com/problems/length-of-last-word/
    // !!! " ".split(" ") => [] !!!
    // !!! " a".split(" ") => ["","a"] !!!
    // !!! "a ".split(" ") => ["a"] !!!
    public int lengthOfLastWord(String s) {
        String[] arrays = s.split(" ");
        if (arrays.length == 0) {
            return 0;
        }
        String last = arrays[arrays.length - 1];
        return last.length();
    }

    // Spiral Matrix
    // http://oj.leetcode.com/problems/spiral-matrix/
    public ArrayList<Integer> spiralOrder(int[][] matrix) {
        ArrayList<Integer> list = new ArrayList<>();
        int row = matrix.length;
        if (row == 0) {
            return list;
        }
        int col = matrix[0].length;
        int layers = (Math.min(row, col) + 1) / 2;
        int xMin, xMax, yMin, yMax, x, y;
        for (int i = 0; i < layers; i++) {
            xMin = i;
            xMax = row - i - 1;
            yMin = i;
            yMax = col - i - 1;
            x = xMin;
            if (xMin == xMax && yMin == yMax) {
                list.add(matrix[xMin][yMin]);
                break;
            }
            for (y = yMin; yMax > yMin && y <= yMax; y++) {
                list.add(matrix[x][y]);
            }
            y = yMax;
            for (x = xMin + 1; xMax > xMin && x <= xMax - 1; x++) {
                list.add(matrix[x][y]);
            }
            x = xMax;
            for (y = yMax; yMax > yMin && xMax > xMin && y >= yMin; y--) {
                list.add(matrix[x][y]);
            }
            y = yMin;
            for (x = xMax - 1; xMax > xMin && x >= xMin + 1; x--) {
                list.add(matrix[x][y]);
            }
        }
        return list;
    }

    // Two Sum
    // http://oj.leetcode.com/problems/two-sum/
    public int[] twoSum(int[] numbers, int target) {
        HashMap<Integer, Integer> list = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            if (list.containsKey(target - numbers[i])) {
                return new int[] { list.get(target - numbers[i]), i + 1 };
            }
            else {
                list.put(numbers[i], i + 1);
            }
        }
        return null;
    }

    // Median of Two Sorted Arrays
    // http://oj.leetcode.com/problems/median-of-two-sorted-arrays/
    public double findMedianSortedArrays(int A[], int B[]) {
        if (A.length == 0 && B.length == 0) {
            return 0;
        }
        else {
            ArrayList<Integer> list = new ArrayList<>();
            int a = 0, b = 0;
            while (a != A.length && b != B.length) {
                if (A[a] < B[b]) {
                    list.add(A[a]);
                    a++;
                }
                else {
                    list.add(B[b]);
                    b++;
                }
            }
            while (a != A.length) {
                list.add(A[a]);
                a++;
            }
            while (b != B.length) {
                list.add(B[b]);
                b++;
            }
            if (list.size() % 2 == 1) {
                return list.get(list.size() / 2);
            }
            else {
                return ((double) list.get(list.size() / 2) + (double) list.get(list.size() / 2 - 1)) / 2;
            }
        }
    }

    // Add Two Numbers
    // http://oj.leetcode.com/problems/add-two-numbers/
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(-1);
        ListNode parent = result;
        int carry = 0;
        while (l1 != null && l2 != null) {
            int sum = l1.val + l2.val + carry;
            result.next = new ListNode(sum % 10);
            carry = sum / 10;
            result = result.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        while (l1 != null) {
            int sum = l1.val + carry;
            result.next = new ListNode(sum % 10);
            carry = sum / 10;
            result = result.next;
            l1 = l1.next;
        }
        while (l2 != null) {
            int sum = l2.val + carry;
            result.next = new ListNode(sum % 10);
            carry = sum / 10;
            result = result.next;
            l2 = l2.next;
        }
        // !!! Don't forget add carry at last !!!
        if (carry != 0) {
            result.next = new ListNode(carry);
        }
        return parent.next;
    }

    // Longest Palindromic Substring
    // http://oj.leetcode.com/problems/longest-palindromic-substring/
    public String longestPalindrome(String s) {
        String result = new String();
        String str;
        for (int i = 0; i < s.length(); i++) {
            str = getPalindrome(s, i);
            result = result.length() > str.length() ? result : str;
        }
        return result;
    }

    private String getPalindrome(String s, int i) {
        int left = i, right = i;
        String str = s.substring(i, i + 1);
        while (left > 0 && right < s.length() - 1 && s.charAt(left - 1) == s.charAt(right + 1)) {
            left--;
            right++;
            str = s.charAt(left) + str + s.charAt(right);
        }
        return str;
    }
}
