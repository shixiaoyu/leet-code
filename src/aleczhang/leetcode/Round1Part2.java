package aleczhang.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import aleczhang.leetcode.datatypes.Interval;
import aleczhang.leetcode.datatypes.ListNode;
import aleczhang.leetcode.datatypes.TreeLinkNode;
import aleczhang.leetcode.datatypes.TreeNode;

@SuppressWarnings("unchecked")
public class Round1Part2 {

    // Subsets II
    public ArrayList<ArrayList<Integer>> subsetsWithDup(int[] num) {
        Arrays.sort(num);
        return subsetsWithDup(num, 0);
    }

    private ArrayList<ArrayList<Integer>> subsetsWithDup(int[] num, int index) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        lists.add(new ArrayList<Integer>());
        if (index == num.length) {
            return lists;
        }
        ArrayList<Integer> list = new ArrayList<>();
        lists.add(list);
        list.add(num[index]);
        while (index + 1 < num.length && num[index + 1] == num[index]) {
            index++;
            ArrayList<Integer> newList = copyList(list);
            newList.add(num[index]);
            lists.add(newList);
            list = newList;
        }
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        ArrayList<ArrayList<Integer>> next = subsetsWithDup(num, index + 1);
        for (ArrayList<Integer> l1 : next) {
            for (ArrayList<Integer> l2 : lists) {
                ArrayList<Integer> l3 = copyList(l2);
                l3.addAll(l1);
                res.add(l3);
            }
        }
        return res;

    }

    private ArrayList<Integer> copyList(ArrayList<Integer> list) {
        ArrayList<Integer> list2 = new ArrayList<>();
        for (Integer i : list) {
            list2.add(i);
        }
        return list2;
    }

    // Gray Code
    public ArrayList<Integer> grayCode(int n) {
        ArrayList<Integer> list = new ArrayList<>();
        int diff = 1 << (n - 1);
        if (n == 0) {
            list.add(0);
            return list;
        }
        else {
            ArrayList<Integer> previous = grayCode(n - 1);
            for (int i = 0; i < previous.size(); i++) {
                list.add(previous.get(i));
            }
            for (int i = previous.size() - 1; i >= 0; i--) {
                list.add(previous.get(i) + diff);
            }
        }
        return list;
    }

    // Spiral Matrix
    public ArrayList<Integer> spiralOrder(int[][] matrix) {
        ArrayList<Integer> res = new ArrayList<>();
        int N = matrix.length;
        if (N == 0) {
            return res;
        }
        int M = matrix[0].length;
        if (N == 1 && M == 1) {
            res.add(matrix[0][0]);
            return res;
        }
        int level = 0, mLen, nLen, r = 0, c = 0, index = 0;
        int maxLevel = (Math.min(N, M) - 1) / 2;
        while (level <= maxLevel) {
            // top
            mLen = M - 2 * level - 1;
            nLen = N - 2 * level - 1;
            while (index < mLen) {
                res.add(matrix[r][c + index]);
                index++;
            }
            // right
            c = c + mLen;
            index = 0;
            while (index < nLen) {
                res.add(matrix[r + index][c]);
                index++;
            }
            // bottom
            r = r + nLen;
            index = 0;
            while (index < mLen && nLen > 0) {
                res.add(matrix[r][c - index]);
                index++;
            }
            if (nLen == 0) {
                res.add(matrix[r][c]);
            }
            // left
            c = c - mLen;
            index = 0;
            while (index < nLen && mLen > 0) {
                res.add(matrix[r - index][c]);
                index++;
            }
            if (nLen != 0 && mLen == 0) {
                res.add(matrix[r][c]);
            }
            // increase level
            r = r - nLen + 1;
            c += 1;
            index = 0;
            level++;
        }
        return res;
    }

    // Regular Expression Matching
    public boolean isMatch(String s, String p) {
        int sLen = s.length(), pLen = p.length();
        ArrayList<String> pp = new ArrayList<>();
        int i = 0;
        while (i < pLen) {
            if (i + 1 < pLen && p.charAt(i + 1) == '*') {
                pp.add(p.substring(i, i + 2));
                i += 2;
            }
            else {
                pp.add(p.substring(i, i + 1));
                i++;
            }
        }
        pLen = pp.size();
        boolean[][] dp = new boolean[sLen + 1][pLen + 1];
        dp[0][0] = true;
        for (i = 0; i <= sLen; i++) {
            for (int j = 1; j <= pLen; j++) {
                boolean hasStar = pp.get(j - 1).length() == 2;
                char pCh = pp.get(j - 1).charAt(0);
                if (i == 0) {
                    dp[i][j] = hasStar && dp[i][j - 1];
                }
                else {
                    char sCh = s.charAt(i - 1);
                    boolean match = pCh == sCh || pCh == '.';
                    dp[i][j] = (hasStar && (dp[i][j - 1] || match && dp[i - 1][j])) ||
                            (match && dp[i - 1][j - 1]);
                }
            }
        }
        return dp[sLen][pLen];
    }

    // Wildcard Matching 
    public boolean isMatch_WildCard(String s, String p) {
        int sLen = s.length();
        int pLen = p.length();
        boolean hasStar = false;
        int starSIndex = -1, starPIndex = -1;
        int sIndex = 0, pIndex = 0;
        while (sIndex < sLen) {
            if (pIndex < pLen && (s.charAt(sIndex) == p.charAt(pIndex) || p.charAt(pIndex) == '?')) {
                sIndex++;
                pIndex++;
            }
            else if (pIndex < pLen && p.charAt(pIndex) == '*') {
                hasStar = true;
                starSIndex = sIndex;
                starPIndex = pIndex;
                pIndex++;
            }
            else if (hasStar) {
                pIndex = starPIndex + 1;
                sIndex = ++starSIndex;
            }
            else {
                return false;
            }
        }
        while (pIndex < pLen) {
            if (p.charAt(pIndex) == '*') {
                pIndex++;
            }
            else {
                return false;
            }
        }
        return true;
    }

    // Minimum Window Substring
    public String minWindow(String S, String T) {
        int len = S.length();
        int count = T.length();
        int[] want = new int[256];
        int[] met = new int[256];
        for (char ch : T.toCharArray()) {
            want[ch]++;
        }
        int start = 0, end = -1, curCount = 0;
        char curChar;
        while (end + 1 < len && curCount < count) {
            end++;
            curChar = S.charAt(end);
            if (met[curChar] < want[curChar]) {
                curCount++;
            }
            met[curChar]++;
        }
        if (curCount < count) {
            return "";
        }
        String res = S.substring(start, end + 1);
        while (end < len) {
            curChar = S.charAt(start);
            while (start + 1 < len && (want[curChar] == 0 || met[curChar] > want[curChar])) {
                start++;
                if (met[curChar] != 0) {
                    met[curChar]--;
                }
                curChar = S.charAt(start);
            }
            if (end - start < res.length()) {
                res = S.substring(start, end + 1);
            }
            end++;
            while (end < S.length() && want[S.charAt(end)] == 0) {
                end++;
            }
            if (end < S.length()) {
                met[S.charAt(end)]++;
            }
        }
        return res;
    }

    public String minWindow_SiChangMethod(String S, String T) {
        if (S.length() == 0 || T.length() == 0) {
            return "";
        }
        int len = Integer.MAX_VALUE;
        String res = new String();
        int charNum = T.length(), count = 0;
        HashMap<Character, Integer> chars = new HashMap<>(26);
        for (char ch : T.toCharArray()) {
            if (chars.containsKey(ch)) {
                chars.put(ch, chars.get(ch) + 1);
            }
            else {
                chars.put(ch, 1);
            }
        }
        LinkedList<Character> charList = new LinkedList<>();
        LinkedList<Integer> posList = new LinkedList<>();
        HashMap<Character, Integer> met = new HashMap<>();
        char curChar;
        for (int i = 0; i < S.length(); i++) {
            curChar = S.charAt(i);
            if (chars.containsKey(curChar)) {
                // if not enough of char is in the list
                if (!met.containsKey(curChar) || met.get(curChar) < chars.get(curChar)) {
                    count++;
                    charList.add(curChar);
                    posList.add(i);
                    if (met.containsKey(curChar)) {
                        met.put(curChar, met.get(curChar) + 1);
                    }
                    else {
                        met.put(curChar, 1);
                    }
                }
                // if there is enough, update the smallest pos
                else {
                    int oldPos = charList.indexOf(curChar);
                    charList.remove(oldPos);
                    charList.add(curChar);
                    posList.remove(oldPos);
                    posList.add(i);
                }
                if (count == charNum) {
                    if (len > posList.getLast() - posList.getFirst()) {
                        len = posList.getLast() - posList.getFirst();
                        res = S.substring(posList.getFirst(), posList.getLast() + 1);
                    }
                }
            }
        }
        return res;
    }

    // Implement strStr()
    public String strStr(String haystack, String needle) {
        int j, l1 = haystack.length(), l2 = needle.length();
        for (int i = 0; i < l1 - l2 + 1; i++) {
            j = 0;
            while (j < needle.length()) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    break;
                }
                j++;
            }
            if (j == needle.length()) {
                return haystack.substring(j);
            }
        }
        return null;
    }

    // Substring with Concatenation of All Words 
    public ArrayList<Integer> findSubstring(String S, String[] L) {
        ArrayList<Integer> res = new ArrayList<>();
        if (L.length == 0 || S.isEmpty()) {
            return res;
        }
        HashMap<String, Integer> words = new HashMap<>();
        for (String word : L) {
            if (words.containsKey(word)) {
                words.put(word, words.get(word) + 1);
            }
            else {
                words.put(word, 1);
            }
        }
        int num = L.length;
        int wordLen = L[0].length();
        int strLen = S.length();
        int wordsNum;
        String word;
        HashMap<String, Integer> met = new HashMap<>();
        for (int i = 0; i <= strLen - num * wordLen; i++) {
            met.clear();
            wordsNum = 0;
            for (int j = 0; j < num; j++) {
                word = S.substring(i + j * wordLen, i + (j + 1) * wordLen);
                if (words.containsKey(word) && (!met.containsKey(word) || met.get(word) < words.get(word))) {
                    met.put(word, met.get(word) == null ? 1 : (met.get(word) + 1));
                    wordsNum++;
                    if (wordsNum == num) {
                        res.add(i);
                    }
                }
                else {
                    break;
                }
            }
        }
        return res;
    }

    // Edit Distance
    public int minDistance(String word1, String word2) {
        int l1 = word1.length();
        int l2 = word2.length();
        int[][] dp = new int[l1 + 1][l2 + 1];
        for (int i = 0; i <= l1; i++) {
            for (int j = 0; j <= l2; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]);
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1]) + 1;
                    if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1]);
                    }
                }
            }
        }
        return dp[l1][l2];
    }

    // Reverse Words in a String
    public String reverseWords(String s) {
        s = s.trim();
        StringBuilder builder = new StringBuilder(s);
        int left = 0, right = 0;
        while (left < builder.length()) {
            if (left > 0 && left < builder.length() && builder.charAt(left) == ' ' && builder.charAt(left + 1) == ' ') {
                builder.deleteCharAt(left);
            }
            else {
                left++;
            }
        }
        int len = builder.length();
        reverseString(builder, 0, len - 1);
        left = 0;
        while (right < builder.length()) {
            while (right < len && builder.charAt(right) != ' ') {
                right++;
            }
            reverseString(builder, left, right - 1);
            right++;
            left = right;
        }
        return builder.toString();
    }

    private void reverseString(StringBuilder builder, int left, int right) {
        char temp;
        while (left < right) {
            temp = builder.charAt(left);
            builder.setCharAt(left, builder.charAt(right));
            builder.setCharAt(right, temp);
            right--;
            left++;
        }
    }

    // Word Ladder II
    class WordNode {
        String word;
        int depth;

        public WordNode(String word, int depth) {
            this.word = word;
            this.depth = depth;
        }
    }

    public ArrayList<ArrayList<String>> findLadders(String start, String end, HashSet<String> dict) {
        if (start.equals(end)) {
            return null;
        }
        dict.add(end);
        Queue<WordNode> queue = new LinkedList<>();
        queue.add(new WordNode(start, 1));

        HashMap<String, ArrayList<String>> tree = new HashMap<>();
        HashMap<String, Integer> met = new HashMap<>();
        met.put(start, 1);

        while (!queue.isEmpty()) {
            WordNode curWord = queue.poll();
            ArrayList<String> neighbors = findNextWordInDict(dict, curWord.word);
            for (String neighbor : neighbors) {
                if (tree.containsKey(neighbor)) {
                    if (met.get(neighbor) == curWord.depth + 1) {
                        tree.get(neighbor).add(curWord.word);
                    }
                }
                else {
                    tree.put(neighbor, new ArrayList<String>());
                    tree.get(neighbor).add(curWord.word);
                    met.put(neighbor, curWord.depth + 1);
                    if (!neighbor.equals(end)) {
                        queue.add(new WordNode(neighbor, curWord.depth + 1));
                    }
                }
            }
        }
        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
        ArrayList<String> path = new ArrayList<>();
        path.add(end);
        generatePaths(res, tree, path, end, start);
        return res;
    }

    ArrayList<String> findNextWordInDict(HashSet<String> dict, String word) {
        ArrayList<String> neighbors = new ArrayList<>();
        char[] wordChars = word.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            char old = wordChars[i];
            for (char n = 'a'; n <= 'z'; n++) {
                if (n != old) {
                    wordChars[i] = n;
                    String temp = String.valueOf(wordChars);
                    if (dict.contains(temp)) {
                        neighbors.add(temp);
                    }
                    wordChars[i] = old;
                }
            }
        }
        return neighbors;
    }

    void generatePaths(ArrayList<ArrayList<String>> res, HashMap<String, ArrayList<String>> tree, ArrayList<String> path, String end, String start) {
        if (!tree.containsKey(end)) {
            return;
        }
        for (String neighbor : tree.get(end)) {
            path.add(neighbor);
            if (neighbor.equals(start)) {
                ArrayList<String> solution = (ArrayList<String>) path.clone();
                Collections.reverse(solution);
                res.add(solution);
            }
            else {
                generatePaths(res, tree, path, neighbor, start);
            }
            path.remove(neighbor);
        }
    }

    // Text Justification
    public ArrayList<String> fullJustify(String[] words, int L) {
        ArrayList<String> list = new ArrayList<>();
        int start = 0, len = 0, wordsLen = 0;
        for (int i = 0; i < words.length; i++) {
            if (len == 0 && len + words[i].length() <= L) {
                len += words[i].length();
                wordsLen += words[i].length();
            }
            else if (len + words[i].length() + 1 <= L) {
                len += words[i].length() + 1;
                wordsLen += words[i].length();
            }
            else {
                list.add(generateLineFromWords(words, start, i, wordsLen, L));
                start = i;
                len = words[i].length();
                wordsLen = words[i].length();
            }
        }
        if (start < words.length) {
            list.add(generateLastLineFromWords(words, start, words.length, wordsLen, L));
        }
        return list;
    }

    private String generateLastLineFromWords(String[] words, int start, int end, int wordsLen, int L) {
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < end; i++) {
            builder.append(words[i]);
            if (i != end - 1) {
                builder.append(" ");
            }
        }
        while (builder.length() < L) {
            builder.append(" ");
        }
        return builder.toString();
    }

    private String generateLineFromWords(String[] words, int start, int end, int wordsLen, int L) {
        StringBuilder builder = new StringBuilder();
        int spaceLen = L - wordsLen;
        int wordsGap = end - start == 1 ? 1 : end - start - 1;
        int space = spaceLen / wordsGap;
        int extra = spaceLen % wordsGap;
        for (int i = start; i < end; i++) {
            builder.append(words[i]);
            if (end - start == 1 || i != end - 1) {
                for (int j = 0; j < space; j++) {
                    builder.append(" ");
                }
            }
            if (i < extra + start) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    // Valid Number 
    public boolean isNumber(String s) {
        s = s.trim();
        int eIndex = s.indexOf('e');
        if (eIndex == -1) {
            return isValidFloat(s);
        }
        else if (eIndex == 0 || eIndex == s.length() - 1) {
            return false;
        }
        else {
            return isValidFloat(s.substring(0, eIndex)) && isValidInt(s.substring(eIndex + 1));
        }
    }

    private boolean isValidPositiveFloat(String s) {
        int dotIndex = s.indexOf('.');
        if (dotIndex == -1) {
            return isValidPositiveInt(s);
        }
        else if (dotIndex == 0) {
            return isValidPositiveInt(s.substring(dotIndex + 1));
        }
        else if (dotIndex == s.length() - 1) {
            return isValidPositiveInt(s.substring(0, dotIndex));
        }
        else {
            return isValidPositiveInt(s.substring(0, dotIndex)) && isValidPositiveInt(s.substring(dotIndex + 1));
        }
    }

    private boolean isValidFloat(String s) {
        if (s.length() > 0 && (s.charAt(0) == '-' || s.charAt(0) == '+')) {
            s = s.substring(1);
        }
        return isValidPositiveFloat(s);
    }

    private boolean isValidInt(String s) {
        if (s.length() > 0 && (s.charAt(0) == '-' || s.charAt(0) == '+')) {
            s = s.substring(1);
        }
        return isValidPositiveInt(s);
    }

    private boolean isValidPositiveInt(String s) {
        if (s.length() == 0) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    // Search in Rotated Sorted Array II
    public boolean search(int[] A, int target) {
        return search(A, target, 0, A.length - 1);
    }

    private boolean search(int[] A, int target, int start, int end) {
        if (end < start) {
            return false;
        }
        else if (start == end) {
            return A[start] == target;
        }
        int midIndex = (start + end) / 2;
        int midVal = A[midIndex];
        if (target == midVal || target == A[start] || target == A[end]) {
            return true;
        }
        if (midVal < A[end] && target > midVal && target < A[end]) {
            return binarySearch(A, target, midIndex + 1, end);
        }
        else if (midVal > A[start] && target > A[start] && target < midVal) {
            return binarySearch(A, target, start, midIndex - 1);
        }
        return search(A, target, start, midIndex - 1) || search(A, target, midIndex + 1, end);
    }

    private boolean binarySearch(int[] A, int target, int start, int end) {
        if (end < start) {
            return false;
        }
        else if (start == end) {
            return A[start] == target;
        }
        int midIndex = (start + end) / 2;
        if (target == A[midIndex]) {
            return true;
        }
        else if (target < A[midIndex]) {
            return binarySearch(A, target, start, midIndex - 1);
        }
        else {
            return binarySearch(A, target, midIndex + 1, end);
        }
    }

    // Palindrome Partitioning II
    public int minCut(String s) {
        int len = s.length();
        boolean[][] pal = new boolean[len][len];
        int[] min = new int[len];
        for (int i = len - 1; i >= 0; i--) {
            int curMin = Integer.MAX_VALUE;
            for (int j = i; j < len; j++) {
                if (i == j || (s.charAt(j) == s.charAt(i) && (j == i + 1 || pal[i + 1][j - 1]))) {
                    pal[i][j] = true;
                    curMin = Math.min(curMin, 1 + (j + 1 >= len ? 0 : min[j + 1]));
                }
            }
            min[i] = curMin;
        }
        return min[0];
    }

    // Merge Intervals
    public ArrayList<Interval> merge(ArrayList<Interval> intervals) {
        ArrayList<Interval> lists = new ArrayList<>();
        for (Interval interval : intervals) {
            insert(lists, interval);
        }
        return lists;
    }

    // Insert Interval 
    public ArrayList<Interval> insert(ArrayList<Interval> intervals, Interval newInterval) {
        ArrayList<Interval> lists = new ArrayList<>();
        for (Interval curInterval : intervals) {
            if (newInterval.start > curInterval.end) {
                lists.add(curInterval);
            }
            else if (newInterval.start <= curInterval.end && newInterval.start >= curInterval.start) {
                if (newInterval.end <= curInterval.end) {
                    newInterval = curInterval;
                }
                else {
                    newInterval.start = curInterval.start;
                }
            }
            else {
                if (newInterval.end < curInterval.start) {
                    lists.add(newInterval);
                    newInterval = curInterval;
                }
                else if (newInterval.end >= curInterval.start && newInterval.end <= curInterval.end) {
                    newInterval.end = curInterval.end;
                }
            }
        }
        lists.add(newInterval);
        return lists;
    }

    public ArrayList<Interval> insertMyMethod(ArrayList<Interval> intervals, Interval newInterval) {
        int first = findInterval(intervals, newInterval.start);
        int second = findInterval(intervals, newInterval.end);
        int start, end;
        if (first % 2 == 0 && second % 2 == 0) {
            start = first / 2;
            end = second / 2 - 1;
            intervals.add(start, newInterval);
            start++;
            end++;
        }
        else if (first % 2 == 0 && second % 2 == 1) {
            start = first / 2;
            end = second / 2;
            intervals.get(end).start = newInterval.start;
            end--;
        }
        else if (first % 2 == 1 && second % 2 == 0) {
            end = second / 2 - 1;
            start = first / 2;
            intervals.get(start).end = newInterval.end;
            start++;
        }
        else {
            start = first / 2;
            end = second / 2;
            intervals.get(start).end = intervals.get(end).end;
            start++;
        }
        while (end - start + 1 > 0) {
            intervals.remove(start);
            end--;
        }
        return intervals;
    }

    private int findInterval(ArrayList<Interval> intervals, int val) {
        int index = 2 * intervals.size();
        for (int i = 0; i < 2 * intervals.size(); i++) {
            if (i == 0) {
                if (intervals.get(i).start > val) {
                    index = 0;
                    break;
                }
            }
            else if (i % 2 == 0) {
                if (val > intervals.get(i / 2 - 1).end && val < intervals.get(i / 2).start) {
                    index = i;
                    break;
                }
            }
            else {
                if (val >= intervals.get(i / 2).start && val <= intervals.get(i / 2).end) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    // Rotate Image 
    public void rotate(int[][] matrix) {
        if (matrix.length == 1) {
            return;
        }
        int temp;
        int n = matrix.length;
        for (int i = 0; i < (n + 1) / 2; i++) {
            for (int j = 0; j < n / 2; j++) {
                temp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                matrix[j][n - 1 - i] = temp;
            }
        }
    }

    // Permutations II
    public ArrayList<ArrayList<Integer>> permuteUnique(int[] num) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int n : num) {
            list.add(n);
        }
        return permuteUnique(list);
    }

    public ArrayList<ArrayList<Integer>> permuteUnique(ArrayList<Integer> list) {
        HashSet<Integer> met = new HashSet<>();
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        ArrayList<ArrayList<Integer>> nextLists;
        if (list.size() == 0) {
            nextLists = new ArrayList<>();
            nextLists.add(new ArrayList<Integer>());
            return nextLists;
        }
        ArrayList<Integer> curList = copyList(list);
        for (Integer num : list) {
            if (!met.contains(num)) {
                curList.remove(num);
                nextLists = permuteUnique(curList);
                curList.add(num);
                for (ArrayList<Integer> eachList : nextLists) {
                    eachList.add(num);
                    res.add(eachList);
                }
                met.add(num);
            }
        }
        return res;
    }

    // Unique Binary Search Trees II 
    public ArrayList<TreeNode> generateTrees(int n) {
        return generateTrees(n, 1);
    }

    private ArrayList<TreeNode> generateTrees(int n, int start) {
        ArrayList<TreeNode> lists = new ArrayList<>();
        if (n == 0) {
            lists.add(null);
            return lists;
        }
        for (int i = 0; i < n; i++) {
            for (TreeNode left : generateTrees(i, start)) {
                for (TreeNode right : generateTrees(n - i - 1, start + i + 1)) {
                    TreeNode cur = new TreeNode(start + i);
                    cur.left = left;
                    cur.right = right;
                    lists.add(cur);
                }
            }
        }
        return lists;
    }

    // Multiply Strings
    public String multiply(String num1, String num2) {
        if ("0".equals(num1) || "0".equals(num2)) {
            return "0";
        }
        StringBuilder res = new StringBuilder();
        int l1 = num1.length();
        int l2 = num2.length();
        int len = l1 + l2 - 1;
        int sum, carry = 0;
        for (int i = 0; i < len; i++) {
            sum = carry;
            for (int j = 0; j <= i; j++) {
                if (j < num1.length() && (i - j) < num2.length()) {
                    sum += (num1.charAt(l1 - 1 - j) - '0') * (num2.charAt(l2 - 1 - i + j) - '0');
                }
            }
            carry = sum / 10;
            sum = sum % 10;
            res.append(sum);
        }
        while (carry != 0) {
            res.append(carry % 10);
            carry /= 10;
        }
        return res.reverse().toString();
    }

    // Recover Binary Search Tree - O(n)
    public void recoverTree(TreeNode root) {
        TreeNode iterator = root;
        ArrayList<TreeNode> list = new ArrayList<>();
        inOrderTranversal(iterator, list);
        TreeNode node1 = null, node2 = null;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).val < list.get(i - 1).val) {
                if (node1 == null) {
                    node1 = list.get(i - 1);
                }
                node2 = list.get(i);
            }
        }
        int temp = node1.val;
        node1.val = node2.val;
        node2.val = temp;
    }

    private void inOrderTranversal(TreeNode node, ArrayList<TreeNode> list) {
        if (node == null) {
            return;
        }
        inOrderTranversal(node.left, list);
        list.add(node);
        inOrderTranversal(node.right, list);
    }

    // Reverse Linked List II
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (m == n) {
            return head;
        }
        ListNode firstNode, first, second, third;
        int temp = m;
        ListNode parent = new ListNode(-1);
        ListNode rootParent = parent;
        parent.next = head;
        while (temp-- > 1) {
            parent = parent.next;
        }
        firstNode = parent.next;
        first = parent;
        second = first.next;
        third = second.next;
        temp = n - m;
        while (temp-- > 0) {
            first = second;
            second = third;
            third = third.next;
            second.next = first;
        }
        parent.next = second;
        firstNode.next = third;
        return rootParent.next;
    }

    // Remove Duplicates from Sorted Array II
    public int removeDuplicates(int[] A) {
        int previous = -1, times = 0;
        int index = 0;
        for (int i = 0; i < A.length; i++) {
            if (i == 0 || A[i] != previous) {
                A[index++] = A[i];
                previous = A[i];
                times = 1;
            }
            else if (A[i] == previous && times < 2) {
                A[index++] = A[i];
                times++;
            }
        }
        return index;
    }

    // sp{el,il,al}l -> spell sppill spall
    // sp{el,il
    // sp{el,{a,b}} -> spela spelb
    // sp{el{a,b,aaa}} -> spela spelb spaaa

    public ArrayList<String> parseBraces(String str) {
        ArrayList<String> res = new ArrayList<>();

        return res;
    }

    // Longest Valid Parentheses
    public int longestValidParentheses(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(s.charAt(i));
            }
            else {
                if (stack.isEmpty() || stack.peek() == ')') {
                    stack.push(s.charAt(i));
                }
                Stack<Character> temp = new Stack<>();
                while (!stack.isEmpty() && stack.peek() == '2') {
                    temp.push(stack.pop());
                }
                boolean used = false;
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();
                    temp.push('2');
                    used = true;
                }
                while (!temp.isEmpty()) {
                    stack.push(temp.pop());
                }
                if (!used) {
                    stack.push(s.charAt(i));
                }
            }
        }
        int max = 0, cur = 0;
        while (!stack.isEmpty()) {
            if (stack.pop() == '2') {
                cur += 2;
            }
            else {
                cur = 0;
            }
            max = Math.max(max, cur);
        }
        return max;
    }

    // Interleaving String
    public boolean isInterleave(String s1, String s2, String s3) {
        int len1 = s1.length(), len2 = s2.length(), len3 = s3.length();
        if (len1 + len2 != len3) {
            return false;
        }
        boolean[][] dp = new boolean[len1 + 1][len2 + 1];
        for (int i = len1; i >= 0; i--) {
            for (int j = len2; j >= 0; j--) {
                if (i == len1 && j == len2) {
                    dp[i][j] = true;
                }
                else if (i == len1) {
                    dp[i][j] = dp[i][j + 1] && (s2.charAt(j) == s3.charAt(i + j));
                }
                else if (j == len2) {
                    dp[i][j] = dp[i + 1][j] && (s1.charAt(i) == s3.charAt(i + j));
                }
                else {
                    dp[i][j] = (s1.charAt(i) == s3.charAt(i + j) && dp[i + 1][j]) || (s2.charAt(j) == s3.charAt(i + j) && dp[i][j + 1]);
                }
            }
        }
        return dp[0][0];
    }

    // Distinct Subsequences
    public int numDistinctDP(String S, String T) {
        int[][] dp = new int[S.length() + 1][T.length() + 1];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
            row[row.length - 1] = 0;
        }
        Arrays.fill(dp[dp.length - 1], 1);
        for (int t = T.length() - 1; t >= 0; t--) {
            for (int s = S.length() - 1; s >= 0; s--) {
                if (S.charAt(s) == T.charAt(t)) {
                    dp[s][t] = dp[s + 1][t + 1] + dp[s + 1][t];
                }
                else {
                    dp[s][t] = dp[s + 1][t];
                }
            }
        }
        return dp[0][0];
    }

    public int numDistinct(String S, String T) {
        int[][] dp = new int[S.length()][T.length()];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }
        return numDistince(S, T, 0, 0, dp);
    }

    private int numDistince(String S, String T, int s, int t, int[][] dp) {
        if (t == T.length()) {
            return 1;
        }
        if (s == S.length()) {
            return 0;
        }
        if (dp[s][t] != -1) {
            return dp[s][t];
        }
        if (S.length() - s < T.length() - t) {
            return 0;
        }
        if (S.charAt(s) == T.charAt(t)) {
            dp[s][t] = numDistince(S, T, s + 1, t + 1, dp) + numDistince(S, T, s + 1, t, dp);
        }
        else {
            while (s < S.length() && S.length() - s >= T.length() - t && S.charAt(s) != T.charAt(t)) {
                s++;
            }
            if (s == S.length()) {
                return 0;
            }
            dp[s][t] = numDistince(S, T, s, t, dp);
        }
        return dp[s][t];
    }

    // Pascal's Triangle II 
    public ArrayList<Integer> getRow(int rowIndex) {
        ArrayList<Integer> list = new ArrayList<>(rowIndex + 1);
        int[] row = new int[rowIndex + 1];
        Arrays.fill(row, 0);
        row[0] = 1;
        int temp;
        for (int i = 0; i < rowIndex; i++) {
            int previous = 0;
            for (int j = 0; j < rowIndex + 1; j++) {
                temp = row[j] + previous;
                previous = row[j];
                row[j] = temp;
            }
        }

        for (int i = 0; i < rowIndex + 1; i++) {
            list.add(row[i]);
        }
        return list;
    }

    // Single Number II
    public int singleNumber(int[] A) {
        int res = 0;
        int sum = 0;
        for (int pos = 0; pos < 32; pos++) {
            sum = 0;
            for (int i : A) {
                if ((i & (1 << pos)) != 0) {
                    sum++;
                }
            }
            if (sum % 3 != 0) {
                res |= (1 << pos);
            }
        }
        return res;
    }

    public int romanToInt(String s) {
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        char[] chars = new char[] { 'I', 'V', 'X', 'L', 'C', 'D', 'M' };
        int[] values = new int[] { 1, 5, 10, 50, 100, 500, 1000 };
        for (int i = 0; i < chars.length; i++) {
            map.put(chars[i], values[i]);
        }
        int res = 0;
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            int value = map.get(c);
            if (i < s.length() - 1 && map.get(s.charAt(i + 1)) > value) {
                res += map.get(s.charAt(i + 1)) - value;
                i += 2;
            }
            else {
                res += value;
                i++;
            }

        }
        return res;
    }

    public String intToRoman(int num) {
        char[][] chars = new char[][] { { 'I', 'V', 'X' },
                { 'X', 'L', 'C' },
                { 'C', 'D', 'M' } };
        StringBuilder builder = new StringBuilder();
        int d, div = 1000;
        for (int i = 0; i < num / 1000; i++) {
            builder.append('M');
        }
        num -= div * (num / div);
        div /= 10;
        for (int i = 2; i >= 0; i--) {
            d = num / div;
            if (d == 9) {
                builder.append(chars[i][0]);
                builder.append(chars[i][2]);
            }
            else if (d == 4) {
                builder.append(chars[i][0]);
                builder.append(chars[i][1]);
            }
            else {
                if (d / 5 == 1) {
                    builder.append(chars[i][1]);
                    d -= 5;
                }
                while (d > 0) {
                    builder.append(chars[i][0]);
                    d--;
                }
            }
            num -= num / div * div;
            div /= 10;
        }
        return builder.toString();
    }

    // Longest Palindromic Substring
    public String longestPalindrome(String s) {
        if (s.length() == 0 || s.length() == 1) {
            return s;
        }
        int max = 0;
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            int len = 1;
            int offset = 0;
            while (i - offset - 1 >= 0 && i + offset + 1 < s.length() && s.charAt(i - offset - 1) == s.charAt(i + offset + 1)) {
                offset++;
                len += 2;
            }
            if (len > max) {
                max = len;
                res = s.substring(i - offset, i + offset + 1);
            }
            len = 0;
            offset = 0;
            while (i - offset - 1 >= 0 && i + offset < s.length() && s.charAt(i - offset - 1) == s.charAt(i + offset)) {
                offset++;
                len += 2;
            }
            if (len > max) {
                max = len;
                res = s.substring(i - offset, i + offset);
            }
        }
        return res;
    }

    // Anagrams 
    public ArrayList<String> anagrams(String[] strs) {
        if (strs.length == 0) {
            return new ArrayList<>();
        }
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String sortedStr = new String(chars);
            if (!map.containsKey((sortedStr))) {
                map.put(sortedStr, new ArrayList<String>());
            }
            map.get(sortedStr).add(str);
        }
        Iterator<Entry<String, ArrayList<String>>> it = map.entrySet().iterator();
        ArrayList<String> res = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<String, ArrayList<String>> entry = it.next();
            if (entry.getValue().size() > 1) {
                res.addAll(entry.getValue());
            }
        }
        return res;
    }

    // Jump Game II 
    public int jump(int[] A) {
        int start = 0, end = 0, len = A.length - 1;
        int step = 0;
        while (start <= end && end < len) {
            int max = 0;
            for (int i = start; i <= end; i++) {
                max = Math.max(max, i + A[i]);
            }
            step++;
            start = end;
            end = max;
        }
        if (end >= len) {
            return step;
        }
        return -1;
    }

    // Jump Game
    public boolean canJump(int[] A) {
        int start = 0, end = 0, len = A.length - 1;
        while (start <= end && end < len) {
            end = Math.max(end, A[start] + start);
            start++;
        }
        return end >= len;
    }

    // First Missing Positive 
    public int firstMissingPositive(int[] A) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : A) {
            if (num > 0) {
                set.add(num);
            }
        }
        int minPositive = 1;
        while (set.contains(minPositive)) {
            minPositive++;
        }
        return minPositive;
    }

    // 3Sum Closest
    public int threeSumClosest(int[] num, int target) {
        int closest = Integer.MIN_VALUE;
        int sum = Integer.MIN_VALUE;
        ArrayList<Integer> nums = new ArrayList<>();
        for (int n : num) {
            nums.add(n);
        }
        Collections.sort(nums);
        for (int i = 0; i < nums.size() - 2; i++) {
            if (i > 0 && nums.get(i).equals(nums.get(i - 1))) {
                continue;
            }
            sum = Integer.MIN_VALUE;
            int j = i + 1;
            int k = nums.size() - 1;
            while (j < k) {
                sum = nums.get(j) + nums.get(k) + nums.get(i) - target;
                closest = checkClosest(target, closest, sum + target);
                if (sum == 0) {
                    return target;
                }
                else if (sum < 0) {
                    do {
                        j++;
                    }
                    while (j < k && nums.get(j).equals(nums.get(j - 1)));
                }
                else if (sum > 0) {
                    do {
                        k--;
                    }
                    while (j < k && nums.get(k).equals(nums.get(k + 1)));
                }
            }
        }
        return closest;
    }

    private int checkClosest(int target, int closest, int newValue) {
        if (closest == Integer.MIN_VALUE) {
            return newValue;
        }
        return (Math.abs(target - closest) < Math.abs(target - newValue) ? closest : newValue);
    }

    // 4 Sum
    public ArrayList<ArrayList<Integer>> fourSum(int[] num, int target) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        ArrayList<Integer> nums = new ArrayList<>();
        for (int n : num) {
            nums.add(n);
        }
        Collections.sort(nums);
        for (int a = 0; a < nums.size() - 3; a++) {
            if (a > 0 && nums.get(a).equals(nums.get(a - 1))) {
                continue;
            }
            for (int b = a + 1; b < nums.size() - 2; b++) {
                if (b > a + 1 && nums.get(b).equals(nums.get(b - 1))) {
                    continue;
                }
                int sum;
                int c = b + 1;
                int d = nums.size() - 1;
                while (c < d) {
                    sum = nums.get(a) + nums.get(b) + nums.get(c) + nums.get(d) - target;
                    if (sum == 0) {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(nums.get(a));
                        list.add(nums.get(b));
                        list.add(nums.get(c));
                        list.add(nums.get(d));
                        lists.add(list);
                        do {
                            c++;
                        }
                        while (c < d && nums.get(c).equals(nums.get(c - 1)));
                        do {
                            d--;
                        }
                        while (c < d && nums.get(d).equals(nums.get(d + 1)));
                    }
                    else if (sum < 0) {
                        c++;
                    }
                    else {
                        d--;
                    }
                }
            }
        }
        return lists;
    }

    // 3Sum 
    public ArrayList<ArrayList<Integer>> threeSum(int[] num) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        ArrayList<Integer> nums = new ArrayList<>();
        for (int n : num) {
            nums.add(n);
        }
        Collections.sort(nums);
        for (int i = 0; i < nums.size() - 2; i++) {
            if (nums.get(i) > 0) {
                continue;
            }
            else if (i > 0 && nums.get(i).equals(nums.get(i - 1))) {
                continue;
            }
            int sum;
            int j = i + 1;
            int k = nums.size() - 1;
            while (j < k) {
                sum = nums.get(j) + nums.get(k) + nums.get(i);
                if (sum == 0) {
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(nums.get(i));
                    list.add(nums.get(j));
                    list.add(nums.get(k));
                    lists.add(list);
                    do {
                        j++;
                    }
                    while (j < k && nums.get(j).equals(nums.get(j - 1)));
                    do {
                        k--;
                    }
                    while (j < k && nums.get(k).equals(nums.get(k + 1)));
                }
                else if (sum < 0) {
                    j++;
                }
                else {
                    k--;
                }
            }
        }
        return lists;
    }

    public ArrayList<ArrayList<Integer>> threeSum_slow(int[] num) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        HashMap<Integer, HashSet<Integer>> used = new HashMap<>();
        for (int i : num) {
            map.put(i, (map.containsKey(i) ? map.get(i) : 0) + 1);
            used.put(i, new HashSet<Integer>());
        }
        for (int i = 0; i < num.length; i++) {
            for (int j = i; j < num.length; j++) {
                map.put(num[i], map.get(num[i]) - 1);
                map.put(num[j], map.get(num[j]) - 1);
                int sum = -num[i] - num[j];
                if (map.containsKey(sum) && map.get(sum) > 0) {
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(num[i]);
                    list.add(num[j]);
                    list.add(sum);
                    Collections.sort(list);
                    if (!used.get(list.get(0)).contains(list.get(1))) {
                        lists.add(list);
                    }
                    used.get(list.get(0)).add(list.get(1));
                }
                map.put(num[i], map.get(num[i]) + 1);
                map.put(num[j], map.get(num[j]) + 1);
            }
        }
        return lists;
    }

    // Container With Most Water
    public int maxArea(int[] height) {
        if (height.length == 1) {
            return 0;
        }
        int left = 0;
        int right = height.length - 1;
        int max = 0;
        while (left < right) {
            max = Math.max(max, (right - left) * Math.min(height[left], height[right]));
            if (height[left] < height[right]) {
                left++;
                while (left < right && height[left] <= height[left - 1]) {
                    left++;
                }
            }
            else if (height[left] > height[right]) {
                right--;
                while (left < right && height[right] <= height[right + 1]) {
                    right--;
                }
            }
            else {
                left++;
                right--;
                while (left < right && height[left] <= height[left - 1]) {
                    left++;
                }
                while (left < right && height[right] <= height[right + 1]) {
                    right--;
                }
            }
        }
        return max;
    }

    // Valid Sudoku
    public boolean isValidSudoku(char[][] board) {
        int[] rows = new int[9];
        int[] cols = new int[9];
        int[] boxes = new int[9];
        int len = 9;
        int bit, index;
        for (int r = 0; r < len; r++) {
            for (int c = 0; c < len; c++) {
                if (board[r][c] != '.') {
                    bit = 1 << (board[r][c] - 48);
                    index = 3 * (r / 3) + c / 3;
                    if ((rows[r] & bit) > 0 || (cols[c] & bit) > 0 || (boxes[index] & bit) > 0) {
                        return false;
                    }
                    rows[r] |= bit;
                    cols[c] |= bit;
                    boxes[index] |= bit;
                }
            }
        }
        return true;
    }

    // Sudoku Solver 
    public boolean solveSudoku(char[][] board) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == '.') {
                    ArrayList<Integer> list = possibleInts(board, r, c);
                    for (int num : list) {
                        board[r][c] = (char) (num + 48);
                        boolean res = solveSudoku(board);
                        if (res) {
                            return res;
                        }
                    }
                    board[r][c] = '.';
                    return false;
                }
                if (r == 8 && c == 8 && board[r][c] != '.') {
                    return true;
                }
            }
        }
        return false;
    }

    private ArrayList<Integer> possibleInts(char[][] board, int r, int c) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int num : new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }) {
            list.add(num);
        }
        for (int i = 0; i < board.length; i++) {
            Integer temp = board[r][i] - 48;
            list.remove(temp);
            temp = board[i][c] - 48;
            list.remove(temp);
        }
        int rBase = 3 * (r / 3);
        int cBase = 3 * (c / 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                list.remove((Integer) (board[rBase + i][cBase + j] - 48));
            }
        }
        return list;
    }

    // Scramble String
    public boolean isScramble(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        else if (s1.equals(s2)) {
            return true;
        }
        boolean res = false;
        HashMap<Character, Integer> leftMatch = new HashMap<>();
        HashMap<Character, Integer> rightMatch = new HashMap<>();
        for (int i = 0; i < s1.length() - 1; i++) {
            char wantChar = s2.charAt(i);
            char leftChar = s1.charAt(i);
            char rightChar = s1.charAt(s1.length() - 1 - i);
            // add wantChar to leftMatch and rightMatch
            Integer temp = leftMatch.get(wantChar);
            leftMatch.put(wantChar, (temp == null ? 0 : temp) + 1);
            temp = rightMatch.get(wantChar);
            rightMatch.put(wantChar, (temp == null ? 0 : temp) + 1);
            // add leftChar to leftMatch
            temp = leftMatch.get(leftChar);
            leftMatch.put(leftChar, (temp == null ? 0 : temp) - 1);
            // add rightChar to rightMatch
            temp = rightMatch.get(rightChar);
            rightMatch.put(rightChar, (temp == null ? 0 : temp) - 1);
            // remove char if its value is 0
            if (leftMatch.containsKey(wantChar) && leftMatch.get(wantChar) == 0) {
                leftMatch.remove(wantChar);
            }
            if (leftMatch.containsKey(leftChar) && leftMatch.get(leftChar) == 0) {
                leftMatch.remove(leftChar);
            }
            if (rightMatch.containsKey(wantChar) && rightMatch.get(wantChar) == 0) {
                rightMatch.remove(wantChar);
            }
            if (rightMatch.containsKey(rightChar) && rightMatch.get(rightChar) == 0) {
                rightMatch.remove(rightChar);
            }
            // if hash is empty, search its two substrings
            // to handle duplicates which causes multiple matches, only return if true 
            if (leftMatch.size() == 0) {
                if ((isScramble(s1.substring(0, i + 1), s2.substring(0, i + 1)) && isScramble(s1.substring(i + 1), s2.substring(i + 1)))) {
                    return true;
                }
            }
            if (rightMatch.size() == 0) {
                if ((isScramble(s1.substring(s2.length() - 1 - i), s2.substring(0, i + 1)) && isScramble(s1.substring(0, s2.length() - 1 - i), s2.substring(i + 1)))) {
                    return true;
                }
            }
        }
        return res;
    }

    // Divide Two Integers 
    // Consider Integer.MIN_VALUE in every case
    // Integer.MIN_VALUE = -2147483648
    // Integer.MAX_VALUE = 2147483647 
    public int divide(int dividend, int divisor) {
        if (divisor == 0) {
            return Integer.MAX_VALUE;
        }
        else if (dividend == 0) {
            return 0;
        }
        if (divisor == Integer.MIN_VALUE) {
            return divide(divide(dividend, divisor >> 1), 2);
        }
        if (dividend == Integer.MIN_VALUE) {
            return (divide(Integer.MAX_VALUE - divisor + 1, divisor) + 1) * (divisor > 0 ? -1 : 1);

        }
        int diviendSign = dividend > 0 ? 1 : -1;
        int divisorSign = divisor > 0 ? 1 : -1;
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);
        int base = 0;

        while (dividend >= divisor) {
            int bottom = divisor;
            int shift = 0;
            while (bottom << 1 > 0 && bottom << 1 < dividend) {
                bottom = bottom << 1;
                shift++;
            }
            dividend -= divisor << shift;
            base += 1 << shift;
        }
        return base * diviendSign * divisorSign;
    }

    // Pow(x,n)
    // Consider Integer.MIN_VALUE alone whenever converting
    // negative integer to positive
    public double pow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if (n == Integer.MIN_VALUE) {
            return 1 / x / pow(x, Integer.MAX_VALUE);
        }
        if (n < 0) {
            return 1 / pow(x, -n);
        }
        double half = pow(x, n / 2);
        return half * half * (n % 2 == 1 ? x : 1);
    }

    // Maximal Rectangle 
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        int[] height = new int[matrix[0].length];
        Arrays.fill(height, 0);
        int maxArea = 0;
        for (int j = 0; j < matrix.length; j++) {
            for (int i = 0; i < matrix[j].length; i++) {
                if (matrix[j][i] == '0') {
                    height[i] = 0;
                }
                else {
                    height[i] += 1;
                }
            }
            int localMax = largestRectangleArea(height);
            maxArea = Math.max(maxArea, localMax);
        }
        return maxArea;
    }

    // Largest Rectangle in Histogram
    public int largestRectangleArea(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int max = 0;
        for (int i = 0; i < height.length; i++) {
            int localHeight, index = -1, localMax;
            while (!stack.isEmpty() && stack.peek() > height[i]) {
                localHeight = stack.pop();
                index = stack.pop();
                localMax = localHeight * (i - index);
                max = Math.max(localMax, max);
            }
            stack.push(index == -1 ? i : index);
            stack.push(height[i]);
        }
        while (!stack.isEmpty()) {
            int hei = stack.pop();
            int index = stack.pop();
            int localMax = hei * (height.length - index);
            max = Math.max(localMax, max);
        }
        return max;
    }

    // N-Queens 
    public ArrayList<String[]> solveNQueens(int n) {
        int[] cols = new int[n];
        Arrays.fill(cols, 0);
        ArrayList<String[]> solutions = new ArrayList<>();
        solveNQueens(0, cols, solutions);
        return solutions;
    }

    public void solveNQueens(int n, int[] cols, ArrayList<String[]> solutions) {
        if (n == cols.length) {
            addSolution(cols, solutions);
        }
        for (int i = 0; i < cols.length; i++) {
            if (isValid(n, i, cols)) {
                cols[n] = i;
                solveNQueens(n + 1, cols, solutions);
            }
        }
    }

    private void addSolution(int[] cols, ArrayList<String[]> solutions) {
        String[] sol = new String[cols.length];
        Arrays.fill(sol, "");
        for (int i = 0; i < cols.length; i++) {
            for (int j = 0; j < cols.length; j++) {
                sol[i] += (cols[i] == j ? "Q" : ".");
            }
        }
        solutions.add(sol);
    }

    private boolean isValid(int n, int colNum, int[] cols) {
        for (int i = 0; i < n; i++) {
            if (cols[i] == colNum) {
                return false;
            }
            if (cols[i] - i == colNum - n) {
                return false;
            }
            if (cols[i] + i == colNum + n) {
                return false;
            }
        }
        return true;
    }

    // Maximum Subarray
    public int maxSubArray(int[] A) {
        if (A.length == 0) {
            return 0;
        }
        int max = A[0];
        int maxElement = A[0];
        int i = 0;
        while (i < A.length) {
            maxElement = Math.max(maxElement, A[i]);
            int sum = 0;
            if (sum + A[i] <= 0) {
                i++;
            }
            else {
                while (i < A.length && sum + A[i] > 0) {
                    sum += A[i++];
                    max = Math.max(max, sum);
                }
            }
        }
        return Math.max(max, maxElement);
    }

    // Longest Consecutive Sequence
    public int longestConsecutive(int[] A) {
        HashSet<Integer> set = new HashSet<>();
        for (int i : A) {
            set.add(i);
        }
        int maxLen = 0;
        for (int i = 0; i < A.length && set.size() > maxLen; i++) {
            if (!set.contains(A[i])) {
                continue;
            }
            set.remove(A[i]);
            int len = 1;
            int bigger = A[i] + 1;
            while (set.contains(bigger)) {
                len++;
                set.remove(bigger);
                bigger++;
            }
            int smaller = A[i] - 1;
            while (set.contains(smaller)) {
                len++;
                set.remove(smaller);
                smaller--;
            }
            maxLen = Math.max(maxLen, len);
        }
        return maxLen;
    }

    // Populating Next Right Pointers in Each Node II 
    public void connect(TreeLinkNode root) {
        TreeLinkNode[] firstChild = getFirstChild(root);
        if (firstChild == null) {
            return;
        }
        TreeLinkNode iterator = firstChild[1];
        TreeLinkNode[] nextChild = getNextChild(firstChild);
        while (nextChild != null) {
            iterator.next = nextChild[1];
            iterator = nextChild[1];
            nextChild = getNextChild(nextChild);
        }
        connect(firstChild[1]);
    }

    TreeLinkNode[] getFirstChild(TreeLinkNode node) {
        TreeLinkNode iterator = node;
        while (iterator != null) {
            if (iterator.left != null) {
                return new TreeLinkNode[] { iterator, iterator.left };
            }
            else if (iterator.right != null) {
                return new TreeLinkNode[] { iterator, iterator.right };
            }
            iterator = iterator.next;
        }
        return null;
    }

    TreeLinkNode[] getNextChild(TreeLinkNode[] nodes) {
        if (nodes[1] == nodes[0].left && nodes[0].right != null) {
            return new TreeLinkNode[] { nodes[0], nodes[0].right };
        }
        return getFirstChild(nodes[0].next);
    }

    // Populating Next Right Pointers in Each Node
    public void connectEasy(TreeLinkNode root) {
        if (root == null) {
            return;
        }
        TreeLinkNode iterator = root;
        while (iterator != null) {
            if (iterator.left != null && iterator.right != null) {
                iterator.left.next = iterator.right;
                if (iterator.next != null) {
                    iterator.right.next = iterator.next.left;
                }
                else {
                    iterator.right.next = null;
                }
            }
            iterator = iterator.next;
        }
        connect(root.left);
    }

    // Best Time to Buy and Sell Stock III
    public int maxProfitTwice(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        int len = prices.length;
        int[] left = new int[len];
        left[0] = 0;
        int[] right = new int[len];
        right[len - 1] = 0;
        int leftMin = prices[0], rightMax = prices[len - 1];
        for (int i = 1; i < prices.length; i++) {
            left[i] = Math.max(prices[i] - leftMin, left[i - 1]);
            leftMin = Math.min(leftMin, prices[i]);
            right[len - 1 - i] = Math.max(rightMax - prices[len - 1 - i], right[len - i]);
            rightMax = Math.max(prices[len - 1 - i], rightMax);
        }
        int totalMax = 0;
        for (int i = 0; i < prices.length; i++) {
            int max = left[i] + right[i];
            totalMax = totalMax > max ? totalMax : max;
        }
        return totalMax;
    }

    // Best Time to Buy and Sell Stock
    public int maxProfit(int[] prices) {
        if (prices.length == 0 || prices.length == 1) {
            return 0;
        }
        int min = prices[0];
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] - min > profit) {
                profit = prices[i] - min;
            }
            if (prices[i] < min) {
                min = prices[i];
            }
        }
        return profit;
    }

    // Word Ladder
    public int ladderLength(String start, String end, HashSet<String> dict) {
        if (start.equals(end)) {
            return 0;
        }
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        dict.add(end);
        int len = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            len++;
            while (size > 0) {
                char[] cur = queue.poll().toCharArray();
                size--;
                for (int i = 0; i < cur.length; i++) {
                    char old = cur[i];
                    for (char n = 'a'; n <= 'z'; n++) {
                        if (n != old) {
                            cur[i] = n;
                            String temp = String.valueOf(cur);
                            if (dict.contains(temp)) {
                                if (temp.equals(end)) {
                                    return len;
                                }
                                queue.add(temp);
                                dict.remove(temp);
                            }
                        }
                    }
                    cur[i] = old;
                }
            }
        }
        return 0;
    }

    // Surrounded Regions

    public void solve(char[][] board) {
        if (board.length == 0) {
            return;
        }
        int row = board.length;
        int col = board[0].length;
        char[][] result = new char[row][col];
        for (char[] array : result) {
            Arrays.fill(array, 'X');
        }
        boolean[][] visited = new boolean[row][col];
        for (boolean[] array : visited) {
            Arrays.fill(array, false);
        }
        for (int r = 0; r < row; r++) {
            if (r == 0 || r == row - 1) {
                for (int c = 0; c < col; c++) {
                    if (board[r][c] == 'O') {
                        bfs(board, visited, result, r, c);
                    }
                }
            }
            if (board[r][0] == 'O') {
                bfs(board, visited, result, r, 0);
            }
            if (board[r][col - 1] == 'O') {
                bfs(board, visited, result, r, col - 1);
            }
        }
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                board[r][c] = result[r][c];
            }
        }
    }

    private void bfs(char[][] board, boolean[][] visited, char[][] result, int row, int col) {
        if (board[row][col] == 'X') {
            return;
        }
        Queue<Integer> queue = new LinkedList<>();
        queue.add(row);
        queue.add(col);
        int r, c;
        while (!queue.isEmpty()) {
            r = queue.poll();
            c = queue.poll();
            if (visited[r][c]) {
                continue;
            }
            else {
                visited[r][c] = true;
            }
            result[r][c] = 'O';
            if (r > 0 && board[r - 1][c] == 'O') {
                queue.add(r - 1);
                queue.add(c);
            }
            if (r < board.length - 1 && board[r + 1][c] == 'O') {
                queue.add(r + 1);
                queue.add(c);
            }
            if (c > 0 && board[r][c - 1] == 'O') {
                queue.add(r);
                queue.add(c - 1);
            }
            if (c < board[0].length - 1 && board[r][c + 1] == 'O') {
                queue.add(r);
                queue.add(c + 1);
            }
        }
    }

    // Palindrome Partitioning
    public ArrayList<ArrayList<String>> partition(String s) {
        return partition(s, new HashMap<String, ArrayList<ArrayList<String>>>());
    }

    private ArrayList<ArrayList<String>> partition(String s, HashMap<String, ArrayList<ArrayList<String>>> map) {
        ArrayList<ArrayList<String>> lists = new ArrayList<>();
        if (s.isEmpty()) {
            lists.add(new ArrayList<String>());
            return lists;
        }
        if (map.containsKey(s)) {
            return map.get(s);
        }
        for (int i = 0; i < s.length(); i++) {
            if (isPalindrom(s.substring(0, i + 1))) {
                ArrayList<ArrayList<String>> oldList = partition(s.substring(i + 1), map);
                for (ArrayList<String> list : oldList) {
                    ArrayList<String> newList = new ArrayList<>();
                    newList.add(s.substring(0, i + 1));
                    newList.addAll(list);
                    lists.add(newList);
                }
            }
        }
        map.put(s, lists);
        return lists;
    }

    private boolean isPalindrom(String str) {
        for (int i = 0; i < str.length() / 2; i++) {
            if (str.charAt(i) != str.charAt(str.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    // Gas Station
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int i = 0, start = 0;
        int tank = 0;
        int len = gas.length;
        int index;
        while (true) {
            if (start >= len) {
                return -1;
            }
            if (i == len) {
                return start;
            }
            index = (start + i) % len;
            if (tank + gas[index] - cost[index] >= 0) {
                i++;
                tank += gas[index] - cost[index];
            }
            else {
                tank = 0;
                start += i + 1;
                i = 0;
            }
        }
    }

    // Word Break II
    public ArrayList<String> wordBreak(String s, Set<String> dict) {
        return wordBreak(s, dict, new HashMap<String, ArrayList<String>>());
    }

    private ArrayList<String> wordBreak(String s, Set<String> dict, HashMap<String, ArrayList<String>> map) {
        ArrayList<String> lists = new ArrayList<>();
        if (s.isEmpty()) {
            lists.add("");
            return lists;
        }
        if (map.containsKey(s)) {
            return map.get(s);
        }
        ArrayList<String> nextLists;
        String subStr;
        for (int i = 1; i <= s.length(); i++) {
            subStr = s.substring(0, i);
            if (dict.contains(subStr)) {
                nextLists = wordBreak(s.substring(i), dict, map);
                for (String str : nextLists) {
                    lists.add(subStr + (str.isEmpty() ? "" : " " + str));
                }
            }
        }
        map.put(s, lists);
        return lists;
    }

    // Candy
    public int candy(int[] ratings) {
        if (ratings.length == 0 || ratings.length == 1) {
            return ratings.length;
        }
        int len = ratings.length;
        int result = len;
        int[] left = new int[len];
        int[] right = new int[len];
        left[0] = 0;
        right[len - 1] = 0;
        int leftCur = 0, rightCur = 0;
        for (int i = 1; i < len; i++) {
            leftCur = ratings[i] > ratings[i - 1] ? leftCur + 1 : 0;
            left[i] = leftCur;
            rightCur = ratings[len - 1 - i] > ratings[len - i] ? rightCur + 1 : 0;
            right[len - 1 - i] = rightCur;
        }
        for (int i = 0; i < len; i++) {
            result += Math.max(left[i], right[i]);
        }
        return result;
    }
}
