package aleczhang.leetcode.datatypes;

public class DoubleListNode {
    public int key;
    public int val;
    public DoubleListNode previous;
    public DoubleListNode next;

    public DoubleListNode(int val) {
        this.val = val;
    }

    public DoubleListNode(int key, int val, DoubleListNode previous, DoubleListNode next) {
        this.key = key;
        this.val = val;
        this.previous = previous;
        this.next = next;
        this.previous.next = this;
        this.next.previous = this;
    }
}
