import fr.i360matt.ssreflection.JsonReflect;

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

    public String text = "truc";
    public int number = 10;
    public One clazz = new One();

    public Map map = new HashMap<>();
    public List<Integer> list = new ArrayList<>();


    public ExampleData () {
        super(new File("C:\\Users\\users\\Downloads\\test.json"));
    }
}
