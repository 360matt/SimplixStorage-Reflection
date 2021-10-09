package fr.i360matt.ssreflection.serialize;

import java.util.*;

public abstract class GenericMap <K,V> {

    public static class Entry <K2, V2> {
        public K2 key;
        public V2 value;

        public Entry (K2 key, V2 value) {
            this.key = key;
            this.value = value;
        }
    }

    private final Map<K,V> map = new HashMap<>();



    protected abstract Entry<String, Object> save (K key, V value);

    protected abstract Entry<K, V> load (String key, Object value);




    public Map<K,V> getMap () {
        return this.map;
    }

    public synchronized void loadAll (final Map<String, Object> from) {
        this.map.clear();
        for (final Map.Entry<String, Object> entry : from.entrySet()) {
            Entry<K, V> entryToPut = this.load(entry.getKey(), entry.getValue());
            this.map.put(entryToPut.key, entryToPut.value);
        }
    }

    public synchronized Map<String, Object> saveAll () {
        final Map<String, Object> res = new HashMap<>();
        for (final Map.Entry<K, V> entry : this.map.entrySet()) {
            Entry<String, Object> entryToPut = this.save(entry.getKey(), entry.getValue());
            res.put(entryToPut.key, entryToPut.value);
        }
        return res;
    }


}
