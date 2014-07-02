package aleczhang.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.junit.Test;

import aleczhang.leetcode.datatypes.DoubleListNode;
import aleczhang.leetcode.datatypes.ListNode;
import aleczhang.leetcode.datatypes.RandomListNode;
import aleczhang.leetcode.datatypes.TreeNode;
import aleczhang.leetcode.datatypes.UndirectedGraphNode;

// Start from March 18
@SuppressWarnings("unchecked")
public class Round2 {

    @Test
    public void test() {
        System.out.println(canCompleteCircuit(new int[] { 1, 2, 3, 3 }, new int[] { 2, 1, 5, 1 }));
    }

    // Palindrome Partitioning II 
    public int minCut(String s) {

    }

    // Clone Graph
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) {
            return null;
        }
        UndirectedGraphNode copy = new UndirectedGraphNode(node.label);
        HashMap<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
        map.put(node, copy);
        HashSet<UndirectedGraphNode> visited = new HashSet<>();
        Queue<UndirectedGraphNode> queue = new LinkedList<>();
        queue.add(node);
        UndirectedGraphNode iterator, copyIterator, copyChild;
        while (!queue.isEmpty()) {
            iterator = queue.poll();
            if (visited.contains(iterator)) {
                continue;
            }
            visited.add(iterator);
            copyIterator = map.get(iterator);
            for (UndirectedGraphNode child : iterator.neighbors) {
                queue.add(child);
                if (map.containsKey(child)) {
                    copyIterator.neighbors.add(map.get(child));
                }
                else {
                    copyChild = new UndirectedGraphNode(child.label);
                    map.put(child, copyChild);
                    copyIterator.neighbors.add(copyChild);
                }
            }
        }
        return copy;
    }

    // Gas Station
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int start = 0, len = 0, tank, dis = gas.length;
        for (start = 0; start < dis; start += 1 + len) {
            tank = gas[start];
            len = 0;
            while (tank >= cost[(start + len) % dis] && len < dis) {
                tank -= cost[(start + len) % dis];
                len++;
                tank += gas[(start + len) % dis];
            }
            if (len == dis) {
                return start;
            }
        }
        return -1;
    }

    // Candy
    public int candy(int[] ratings) {
        int len = ratings.length;
        int[] left = new int[len], right = new int[len];
        for (int i = 1; i < len; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            }
            else {
                left[i] = 0;
            }
            if (ratings[len - 1 - i] > ratings[len - i]) {
                right[len - 1 - i] = right[len - i] + 1;
            }
            else {
                right[len - 1 - i] = 0;
            }
        }
        int sum = len;
        for (int i = 0; i < len; i++) {
            sum += Math.max(left[i], right[i]);
        }
        return sum;
    }

    // Single Number II 
    public int singleNumber(int[] A) {
        int sum = 0, count;
        for (int i = 0; i < 32; i++) {
            count = 0;
            for (int n : A) {
                count += (n >> i) & 1;
            }
            if (count % 3 == 1) {
                sum += 1 << i;
            }
        }
        return sum;
    }

    // Single Number
    public int singleNumber1(int[] A) {
        int res = 0;
        for (int num : A) {
            res ^= num;
        }
        return res;
    }

    // Copy List with Random Pointer 
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return null;
        }
        RandomListNode newHead = new RandomListNode(head.label);
        HashMap<RandomListNode, RandomListNode> map = new HashMap<>();
        map.put(head, newHead);
        RandomListNode iterator = head, newIterator = newHead;
        while (iterator.next != null) {
            newIterator.next = new RandomListNode(iterator.next.label);
            iterator = iterator.next;
            newIterator = newIterator.next;
            map.put(iterator, newIterator);
        }
        iterator = head;
        newIterator = newHead;
        while (iterator != null) {
            newIterator.random = map.get(iterator.random);
            iterator = iterator.next;
            newIterator = newIterator.next;
        }
        return newHead;
    }

    // Word Break II
    public ArrayList<String> wordBreak(String s, Set<String> dict) {
        int len = s.length();
        ArrayList<ArrayList<Integer>> dp = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            dp.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < len; i++) {
            if (dict.contains(s.substring(0, i + 1))) {
                dp.get(i).add(0);
            }
            for (int j = 0; j < i; j++) {
                if (dp.get(j).size() != 0 && dict.contains(s.substring(j + 1, i + 1))) {
                    dp.get(i).add(j + 1);
                }
            }
        }
        ArrayList<String> res = new ArrayList<>();
        constructFromDp(s, dp, res, "", len - 1);
        return res;
    }

    private void constructFromDp(String s, ArrayList<ArrayList<Integer>> dp, ArrayList<String> res, String path, int index) {
        for (int previous : dp.get(index)) {
            String next = s.substring(previous, index + 1) + (path.isEmpty() ? "" : " " + path);
            if (previous == 0) {
                res.add(next);
            }
            else {
                constructFromDp(s, dp, res, next, previous - 1);
            }
        }
    }

    // Word Break
    public boolean wordBreak1(String s, Set<String> dict) {
        int len = s.length();
        boolean dp[] = new boolean[len];
        for (int i = 0; i < len; i++) {
            if (dict.contains(s.subSequence(0, i + 1))) {
                dp[i] = true;
            }
            else {
                for (int j = 0; j < i; j++) {
                    if (dp[j] && dict.contains(s.subSequence(j + 1, i + 1))) {
                        dp[i] = true;
                        break;
                    }
                }
            }
        }
        return dp[len - 1];
    }

    // Linked List Cycle II 
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;
        boolean started = false;
        while ((!started || slow != fast) && fast != null && fast.next != null && fast.next.next != null) {
            started = true;
            slow = slow.next;
            fast = fast.next.next;
        }
        if (fast == null || fast.next == null || fast.next.next == null) {
            return null;
        }
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    // Linked List Cycle
    public boolean hasCycle(ListNode head) {
        HashSet<ListNode> set = new HashSet<>();
        while (head != null) {
            if (set.contains(head)) {
                return true;
            }
            set.add(head);
            head = head.next;
        }
        return false;
    }

    // Reorder List
    public void reorderList(ListNode head) {
        if (head == null) {
            return;
        }
        Stack<ListNode> stack = new Stack<>();
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = slow;
        slow = slow.next;
        fast.next = null;
        while (slow != null) {
            stack.add(slow);
            slow = slow.next;
        }
        slow = head;
        while (!stack.isEmpty()) {
            fast = stack.pop();
            fast.next = slow.next;
            slow.next = fast;
            slow = slow.next.next;
        }
    }

    // Binary Tree Preorder Traversal
    public ArrayList<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        TreeNode iterator;
        while (!stack.isEmpty()) {
            iterator = stack.pop();
            if (iterator == null) {
                continue;
            }
            res.add(iterator.val);
            stack.push(iterator.right);
            stack.push(iterator.left);
        }
        return res;
    }

    // Binary Tree Postorder Traversal
    public ArrayList<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        HashSet<TreeNode> visited = new HashSet<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        TreeNode iterator;
        while (!stack.isEmpty()) {
            iterator = stack.pop();
            if (iterator == null) {
                continue;
            }
            if (visited.contains(iterator)) {
                res.add(iterator.val);
            }
            else {
                stack.push(iterator);
                stack.push(iterator.right);
                stack.push(iterator.left);
                visited.add(iterator);
            }
        }
        return res;
    }

    public ArrayList<Integer> postorderTraversalRecursion(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        postorderTraversalRecursion(root, list);
        return list;
    }

    private void postorderTraversalRecursion(TreeNode node, ArrayList<Integer> res) {
        if (node == null) {
            return;
        }
        postorderTraversalRecursion(node.left, res);
        postorderTraversalRecursion(node.right, res);
        res.add(node.val);
    }

    // Subsets
    public ArrayList<ArrayList<Integer>> subsets(int[] num) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for (int i = 0; i <= num.length; i++) {
            subsetsRecursion(num, i, 0, res, new ArrayList<Integer>());
        }
        return res;
    }

    @SuppressWarnings("unchecked")
    private void subsetsRecursion(int[] num, int target, int start, ArrayList<ArrayList<Integer>> res, ArrayList<Integer> list) {
        if (list.size() == target) {
            res.add((ArrayList<Integer>) list.clone());
            return;
        }
        for (int i = start; i < num.length - (target - list.size()) + 1; i++) {
            list.add(num[i]);
            subsetsRecursion(num, target, i + 1, res, list);
            list.remove((Integer) num[i]);
        }
    }

    // Subsets 2
    public ArrayList<ArrayList<Integer>> subsetsWithDup(int[] num) {
        Arrays.sort(num);
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for (int i = 0; i <= num.length; i++) {
            subsetsWithDupRecursion(num, i, 0, res, new ArrayList<Integer>());
        }
        return res;
    }

    @SuppressWarnings("unchecked")
    private void subsetsWithDupRecursion(int[] num, int target, int start, ArrayList<ArrayList<Integer>> res, ArrayList<Integer> list) {
        if (list.size() == target) {
            res.add((ArrayList<Integer>) list.clone());
            return;
        }
        for (int i = start; i < num.length - (target - list.size()) + 1; i++) {
            if (i != start && num[i] == num[i - 1]) {
                continue;
            }
            list.add(num[i]);
            subsetsWithDupRecursion(num, target, i + 1, res, list);
            list.remove((Integer) num[i]);
        }
    }

    // LRU Cache
    class LRUCache {

        public DoubleListNode node;
        public Map<Integer, DoubleListNode> map;
        public int capacity;

        public LRUCache(int capacity) {
            this.node = new DoubleListNode(-1);
            this.node.previous = node;
            this.node.next = node;
            this.capacity = capacity;
            this.map = new HashMap<>();
        }

        public int get(int key) {
            if (!map.containsKey(key)) {
                return -1;
            }
            DoubleListNode node = map.get(key);
            removeNode(key);
            set(key, node.val);
            return node.val;
        }

        public void set(int key, int value) {
            DoubleListNode node;
            if (map.containsKey(key)) {
                removeNode(key);
            }
            else if (map.size() == this.capacity) {
                removeNode(this.node.previous.key);
            }
            node = new DoubleListNode(key, value, this.node, this.node.next);
            map.put(key, node);
        }

        private void removeNode(int key) {
            DoubleListNode node = map.get(key);
            node.previous.next = node.next;
            node.next.previous = node.previous;
            map.remove(key);
        }
    }

    // Insertion Sort List
    public ListNode insertionSortList(ListNode head) {
        ListNode res = new ListNode(Integer.MIN_VALUE);
        ListNode nextNode;
        while (head != null) {
            nextNode = head.next;
            insertListNode(res, head);
            head = nextNode;
        }
        return res.next;
    }

    private void insertListNode(ListNode parent, ListNode node) {
        while (parent.next != null && parent.next.val < node.val) {
            parent = parent.next;
        }
        ListNode temp = parent.next;
        parent.next = node;
        node.next = temp;
    }

    // Sort List 
    public ListNode sortList(ListNode head) {
        ListNode end = head;
        while (end != null && end.next != null) {
            end = end.next;
        }
        ListNode headParent = new ListNode(-1);
        headParent.next = head;
        return sortList(headParent, end).next;
    }

    private ListNode sortList(ListNode headParent, ListNode end) {
        if (headParent.next == null || headParent.next == end || headParent == end) {
            return headParent;
        }
        int pivot = headParent.next.val;
        moveListNode(headParent, headParent.next, end);
        ListNode iterator = headParent.next;
        ListNode iteratorParent = headParent;
        ListNode tailHead = end.next;
        ListNode tailEnd = tailHead;
        ListNode iterationEnd = tailHead;
        while (iterator != null && iterator != iterationEnd) {
            if (iterator.val > pivot) {
                moveListNode(iteratorParent, iterator, tailEnd);
                tailEnd = tailEnd.next;
                iterator = iteratorParent.next;
            }
            else if (iterator.val == pivot) {
                moveListNode(iteratorParent, iterator, tailHead);
                if (tailEnd == tailHead) {
                    tailEnd = tailEnd.next;
                }
                tailHead = tailHead.next;

                iterator = iteratorParent.next;
            }
            else {
                iteratorParent = iterator;
                iterator = iterator.next;
            }
        }
        sortList(tailHead, tailEnd);
        return sortList(headParent, iteratorParent);
    }

    private void moveListNode(ListNode parent, ListNode node, ListNode target) {
        ListNode temp = target.next;
        parent.next = node.next;
        target.next = node;
        node.next = temp;
    }

    // Evaluate Reverse Polish Notation
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<Integer>();
        for (String str : tokens) {
            if (!str.equals("+") && !str.equals("-") && !str.equals("*") && !str.equals("/")) {
                stack.push(Integer.valueOf(str));
            }
            else {
                int first = stack.pop();
                int second = stack.pop();
                if (str.equals("+")) {
                    stack.push(first + second);
                }
                else if (str.equals("-")) {
                    stack.push(second - first);
                }
                else if (str.equals("*")) {
                    stack.push(first * second);
                }
                else if (str.equals("/")) {
                    stack.push(second / first);
                }
            }
        }
        return stack.pop();
    }

    // Longest Substring Without Repeating Characters 
    public int lengthOfLongestSubstring(String s) {
        if (s.length() <= 1) {
            return s.length();
        }
        int start = 0, end = 1, max = 0, len = s.length();
        while (end < len) {
            while (end < len && s.substring(start, end).indexOf(s.charAt(end)) == -1) {
                max = Math.max(end - start + 1, max);
                end++;
            }
            if (end < len) {
                start += s.substring(start, end).indexOf(s.charAt(end)) + 1;
            }
        }
        return max;
    }

    // Flatten Binary Tree to Linked List 
    public void flatten(TreeNode root) {
        if (root != null) {
            flattenRecursion(root);
        }
    }

    private TreeNode flattenRecursion(TreeNode node) {
        TreeNode rightEnd = node;
        if (node.right != null) {
            rightEnd = flattenRecursion(node.right);
        }
        TreeNode leftEnd = node;
        if (node.left != null) {
            leftEnd = flattenRecursion(node.left);
            leftEnd.right = node.right;
            node.right = node.left;
            node.left = null;
        }
        return rightEnd == node ? leftEnd : rightEnd;
    }

    // Convert tree to linked list
    public ListNode convertTreeToLinkedNode(TreeNode root) {
        ListNode parent = new ListNode(-1);
        convertTreeToLinkedNode(root, parent);
        return parent.next;
    }

    private ListNode convertTreeToLinkedNode(TreeNode node, ListNode parentNode) {
        if (node == null) {
            return parentNode;
        }
        parentNode = convertTreeToLinkedNode(node.left, parentNode);
        parentNode.next = new ListNode(node.val);
        return convertTreeToLinkedNode(node.right, parentNode.next);

    }

    // Binary Tree Zigzag Level Order Traversal
    public ArrayList<ArrayList<Integer>> zigzagLevelOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        if (root == null) {
            return lists;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int size;
        ArrayList<Integer> list;
        TreeNode cur;
        boolean left = true;
        while (!queue.isEmpty()) {
            size = queue.size();
            list = new ArrayList<>();
            while (size-- > 0) {
                cur = queue.poll();
                if (cur == null) {
                    continue;
                }
                list.add(cur.val);
                queue.add(cur.left);
                queue.add(cur.right);
            }
            if (!left && !list.isEmpty()) {
                Collections.reverse(list);
                lists.add(list);
            }
            left = !left;
        }
        return lists;
    }

    // Path Sum II
    public ArrayList<ArrayList<Integer>> pathSum(TreeNode root, int sum) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        pathSumRecursion(root, sum, 0, new ArrayList<Integer>(), lists);
        return lists;
    }

    private void pathSumRecursion(TreeNode node, int sum, int preSum, ArrayList<Integer> list, ArrayList<ArrayList<Integer>> lists) {
        if (node == null) {
            return;
        }
        list.add(node.val);
        preSum += node.val;
        if (node.left == null && node.right == null && preSum == sum) {
            lists.add((ArrayList<Integer>) list.clone());
        }
        else {
            pathSumRecursion(node.left, sum, preSum, list, lists);
            pathSumRecursion(node.right, sum, preSum, list, lists);
        }
        list.remove(list.size() - 1);
    }

    // Sum Root to Leaf Numbers
    public int sumNumbers(TreeNode root) {
        return sumNumbers(root, 0);
    }

    private int sumNumbers(TreeNode node, int sum) {
        if (node == null) {
            return sum;
        }
        sum = 10 * sum + node.val;
        if (node.left == null && node.right == null) {
            return sum;
        }
        else {
            return (node.left != null ? sumNumbers(node.left, sum) : 0) + (node.right != null ? sumNumbers(node.right, sum) : 0);
        }
    }

    // Binary Tree Maximum Path Sum
    public int maxPathSum(TreeNode root) {
        HashMap<TreeNode, Integer> map = new HashMap<>();
        return maxPathSum(root, map);
    }

    private int maxPathSum(TreeNode root, HashMap<TreeNode, Integer> maxSinglePath) {
        if (root == null) {
            return Integer.MIN_VALUE;
        }
        int sum = Math.max(maxPath(root.left, maxSinglePath), 0)
                + Math.max(maxPath(root.right, maxSinglePath), 0) + root.val;
        sum = Math.max(sum, maxPathSum(root.left, maxSinglePath));
        sum = Math.max(sum, maxPathSum(root.right, maxSinglePath));
        return sum;
    }

    private int maxPath(TreeNode node, HashMap<TreeNode, Integer> maxSinglePath) {
        if (maxSinglePath.containsKey(node)) {
            return maxSinglePath.get(node);
        }
        if (node == null) {
            return 0;
        }
        int sum = node.val + Math.max(0, Math.max(maxPath(node.left, maxSinglePath), maxPath(node.right, maxSinglePath)));
        maxSinglePath.put(node, sum);
        return sum;
    }

    public int maxPathSum_changsi(TreeNode root) {
        int[] r = maxPathSum_r(root);
        return r[0];
    }

    public int[] maxPathSum_r(TreeNode root) {
        if (root == null) {
            return new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE };
        }
        int[] left = maxPathSum_r(root.left);
        int[] right = maxPathSum_r(root.right);
        int[] r = new int[2];
        r[0] = Math.max(Math.max(left[0], right[0]), (left[1] >= 0 ? left[1] : 0) + (right[1] >= 0 ? right[1] : 0) + root.val);
        r[1] = Math.max((right[1] >= 0 ? right[1] : 0) + root.val, (left[1] >= 0 ? left[1] : 0) + root.val);
        return r;
    }
}
