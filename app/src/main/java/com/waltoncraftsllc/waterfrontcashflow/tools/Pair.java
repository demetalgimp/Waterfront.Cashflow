package com.waltoncraftsllc.waterfrontcashflow.tools;

public class Pair<K, V> {
    private K key;
    private V value;
//    public Pair() {}
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    public K getKey() { return key; }
    public V getValue() { return value; }
    public void setKey(K key) { this.key = key; }
}