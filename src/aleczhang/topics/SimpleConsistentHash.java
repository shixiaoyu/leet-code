package aleczhang.topics;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

import aleczhang.leetcode.datatypes.HashFunction;

public class SimpleConsistentHash<T> {
    private final int replicaNum;
    private final HashFunction hashFunction;
    private final SortedMap<Integer, T> circle = new TreeMap<>();

    public SimpleConsistentHash(int replicaNum, HashFunction hashFunction, Collection<T> nodes) {
        this.replicaNum = replicaNum;
        this.hashFunction = hashFunction;
        for (T node : nodes) {
            addNode(node);
        }
    }

    public void addNode(T node) {
        for (int i = 0; i < replicaNum; i++) {
            circle.put(hashFunction.hash(node.toString() + i), node);
        }
    }

    public void removeNode(T node) {
        for (int i = 0; i < replicaNum; i++) {
            circle.remove(hashFunction.hash(node.toString() + 1));
        }
    }

    public T getNode(Object key) {
        if (circle.isEmpty()) {
            return null;
        }
        int hash = hashFunction.hash(key);
        if (!circle.containsKey(hash)) {
            SortedMap<Integer, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }
}
