import fr.i360matt.ssreflection.JsonReflect;
import fr.i360matt.ssreflection.serialize.GenericList;
import fr.i360matt.ssreflection.serialize.GenericMap;

import java.io.File;
import java.util.*;

/**
 *
 */
public class ExampleData extends JsonReflect {

    public static class One {
        public String one_text = "bidule";
        public int one_number = 90;
        public Two one_clazz = new Two();
    }

    public static class Two {
        public String sec_text = "chouette";
        public long sec_number = 800;
    }

    public static class ListOne extends GenericList<One> {

        @Override
        protected Map<String, Object> save (final One obj) {
            HashMap<String, Object> res = new HashMap<>();
            res.put("one_text", obj.one_text);
            res.put("one_number", obj.one_number);
          //  res.put("one_clazz", obj.one_clazz);
            return res;
        }

        @Override
        protected One load (final Map<String, Object> from) {
            One res = new One();
            res.one_text = (String) from.get("one_text");
            res.one_number = (int) from.get("one_number");
          //  res.one_clazz = (Two) from.get("one_clazz");
            return res;
        }
    }

    public static class CustomMap extends GenericMap<String, Two> {

        @Override
        protected Entry<String, Object> save (final String key, final Two value) {
            HashMap<String, Object> twoRes = new HashMap<>();
            twoRes.put("sec_text", value.sec_text);
            twoRes.put("sec_number", (int) value.sec_number);

            return new Entry<>(key, twoRes);
        }

        @Override
        protected Entry<String, Two> load (final String key, final Object value) {
            HashMap<String, Object> twoIn = (HashMap<String, Object>) value;

            System.out.println(twoIn.get("sec_number"));

            Two twoRes = new Two();
            twoRes.sec_text = (String) twoIn.get("sec_text");
            twoRes.sec_number = (Integer) twoIn.get("sec_number");

            return new Entry<>(key, twoRes);
        }
    }

    public String text = "truc";
    public int number = 10;
    public One clazz = new One();

    public Map map = new HashMap<>();
    public List<Integer> list = new ArrayList<>();

    public ListOne oneList = new ListOne();

    public CustomMap customMap = new CustomMap();


    public ExampleData () {
        super(new File("C:\\Users\\users\\Downloads\\test.json"));
    }
}
