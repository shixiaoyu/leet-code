package aleczhang.topics;

import java.util.HashSet;

interface Triangle {

    /**
     * Three segments of lengths A, B, C form a triangle iff
     * 
     * A + B > C B + C > A A + C > B
     * 
     * e.g. 6, 4, 5 can form a triangle 10, 2, 7 can't
     * 
     * Given a list of segments lengths algorithm should find at least one triplet of segments that form a triangle (if any).
     * 
     * Method should return an array of either: - 3 elements: segments that form a triangle (i.e. satisfy the condition above) - empty array if there are no such segments
     */
    // 2 3 5 8 13 21 33
    int[] getTriangleSides(int[] segments);
}

public class LinkedIn_2ndPhone {

    // Question Description: Given a sorted array that has been transposed (that is, a portion has been removed from one end and attached to the other), write a function to determine if a given number is present in the array.

    // Examples: (6 7 1 2 3 4 5) => find 1 or 4; (4 5 6 7 1 2 3) => find 1 or 6

    public static boolean isInList(float targetValue, float[] list) {
        if (list.length == 0) {
            return false;
        }
        else if (list.length == 1) {
            return list[0] == targetValue;
        }
        return isInList(targetValue, list, 0, list.length - 1);
    }

    // 6, 6, 6, 6, 1, 2, 3
    // left =0 right = 2, midIndex = 1, midVal = 7
    public static boolean isInList(float targetValue, float[] list, int left, int right) {
        if (left == right) {
            return list[left] == targetValue;
        }
        int midIndex = (left + right) / 2;
        float midValue = list[midIndex];
        if (midValue == targetValue || list[left] == targetValue || list[right] == targetValue) {
            return true;
        }
        else if (midValue > targetValue) {
            if (list[left] <= midValue) {
                if (list[left] < targetValue) {
                    return isInList(targetValue, list, left, midIndex - 1);
                }
                else {
                    return isInList(targetValue, list, midIndex + 1, right);
                }
            }
            else {
                return isInList(targetValue, list, left, midIndex - 1);
            }
        }
        else {
            if (list[right] >= midValue) {
                if (list[right] > targetValue) {
                    return isInList(targetValue, list, midIndex + 1, right);
                }
                else {
                    return isInList(targetValue, list, left, midIndex - 1);
                }
            }
            else {
                return isInList(targetValue, list, midIndex + 1, right);
            }
        }

    }

    public interface FirstCommonAncestor {

        /**
         * Given two nodes of a tree, method should return the deepest common ancestor of those nodes.
         * 
         * A / \ B C / \ D E / \ G F
         * 
         * commonAncestor(D, F) = B commonAncestor(C, G) = A
         */
        Node commonAncestor(Node one, Node two);
    }

    class Node {

        final Node parent;
        final Node left;
        final Node right;

        public Node(Node parent, Node left, Node right) {
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        boolean isRoot() {
            return parent == null;
        }
    }

    Node commonAncestor(Node one, Node two) {
        HashSet<Node> path = new HashSet<>();
        Node cur = one;
        while (!cur.isRoot()) {
            path.add(cur);
            cur = cur.parent;
        }
        path.add(cur);
        cur = two;
        while (!cur.isRoot()) {
            if (path.contains(cur)) {
                return cur;
            }
            else {
                cur = cur.parent;
            }
        }
        return path.contains(cur) ? cur : null;
    }

}
