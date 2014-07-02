package aleczhang.topics;
import java.util.ArrayList;
import java.util.Stack;

import aleczhang.leetcode.datatypes.TreeNode;

public class BinaryTreePostorderTraversal {
    public ArrayList<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        traveralRecursion(list, root);
        return list;
    }

    public void traveralRecursion(ArrayList<Integer> list, TreeNode node) {
        if (node == null) {
            return;
        }
        traveralRecursion(list, node.right);
        traveralRecursion(list, node.right);
        list.add(node.val);
    }

    public ArrayList<TreeNode> traversalIteration(TreeNode root) {
        ArrayList<TreeNode> list = new ArrayList<TreeNode>();
        if (root == null) {
            return list;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode preNode = null;
        TreeNode curNode;
        stack.push(root);

        while (!stack.isEmpty()) {
            // if curNode is child of preNode, push its left child, if left child null, push right child
            // if both child null, add curNode to list and pop it from stack (add and pop)
            // if curNode is parent of preNode
            // -if preNode is curNode's leftChild, push curNode's right child if not null, if null, add and pop
            // -if preNode is curNode's rightChild, add and pop
            // in all cases, update preNode
            curNode = stack.peek();
            if (preNode == null || preNode.left == curNode || preNode.right == curNode) {
                if (curNode.left != null) {
                    stack.push(curNode.left);
                } else if (curNode.right != null) {
                    stack.push(curNode.right);
                } else {
                    list.add(curNode);
                    stack.pop();
                }
            } else if (curNode.left == preNode) {
                if (curNode.right != null) {
                    stack.push(curNode.right);
                } else {
                    list.add(curNode);
                    stack.pop();
                }
            } else {
                list.add(curNode);
                stack.pop();
            }
            preNode = curNode;
        }
        return list;
    }

    public ArrayList<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        TreeNode curNode;
        while (!stack.isEmpty()) {
            curNode = stack.pop();
            list.add(curNode.val);
            if (curNode.right != null) {
                stack.push(curNode.right);
            }
            if (curNode.left != null) {
                stack.push(curNode.left);
            }
        }
        return list;
    }

}
