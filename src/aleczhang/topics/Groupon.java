package aleczhang.topics;

import java.util.Random;

import org.junit.Test;

import aleczhang.leetcode.datatypes.TreeNode;

public class Groupon {

    @Test
    public void test() {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(8);
        System.out.println(lowestCommonAncestor(root, 2, 8).val);
        System.out.println(lowestCommonAncestor(root, 2, 4).val);
        System.out.println(lowestCommonAncestor(root, 2, 3).val);
        System.out.println(lowestCommonAncestor(root, 4, 7).val);
    }

    // ???????????????weight???string???list?????????????????????string????????????????????????weight?????????
    public int getWeightRandom(int[] nums) {
        int len = nums.length;
        int[] accu = new int[len];
        accu[0] = nums[0];
        for (int i = 1; i < len; i++) {
            accu[i] = nums[i] + accu[i - 1];
        }
        int rand = new Random().nextInt(accu[len - 1]);
        int l = 0, r = len - 1, mid;
        while (l + 1 < r) {
            mid = (l + r) / 2;
            if (rand > accu[mid]) {
                l = mid + 1;
            }
            else if (rand < accu[mid]) {
                r = mid;
            }
            else {
                return nums[mid + 1];
            }
        }
        return nums[r];
    }

    // BST???Lowest common ancestor
    public TreeNode lowestCommonAncestor(TreeNode root, int val1, int val2) {
        int val = root.val;
        if (val == val1 || val == val2 || (val < val1 && val > val2) || (val > val1 && val < val2)) {
            return root;
        }
        else if (val < val1 && val < val2) {
            return lowestCommonAncestor(root.right, val1, val2);
        }
        else {
            return lowestCommonAncestor(root.left, val1, val2);
        }

    }

    // word transformation with dictionary

}
