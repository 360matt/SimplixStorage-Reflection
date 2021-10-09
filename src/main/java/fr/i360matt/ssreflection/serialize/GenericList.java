package fr.i360matt.ssreflection.serialize;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class GenericList <T> {

    private final List<T> list = new ArrayList<>();



    protected abstract Map<String, Object> save (T obj);

    protected abstract T load (Map<String, Object> from);


    public List<T> getList () {
        return this.list;
    }

    public synchronized void loadAll (final List<Map<String, Object>> from) {
        this.list.clear();
        for (final Map<String, Object> map : from) {
            this.list.add(this.load(map));
        }
    }

    public synchronized List<Map<String, Object>> saveAll () {
        final List<Map<String, Object>> res = new ArrayList<>();
        for (final T obj : this.list) {
            res.add(this.save(obj));
        }
        return res;
    }


}
