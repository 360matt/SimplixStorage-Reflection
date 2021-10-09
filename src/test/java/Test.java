import fr.i360matt.ssreflection.internal.TranslateValue;
import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.Assert.*;

import java.util.*;

public class Test {

    @org.junit.Test
    public void main () throws Throwable {

        ExampleData configReflect = new ExampleData();


        /* you can remove this code (only if file is generated) */
        /* */  configReflect.load();
        /* */  configReflect.text = "Heey";
        /* */  configReflect.number = 666;
        /* */  configReflect.map.put("ok", "kayak");
        /* */  configReflect.list = Arrays.asList(20, 50);




        ExampleData.One one = new ExampleData.One();
        one.one_number = 999;
        one.one_text = "Martin";

        System.out.println(configReflect.oneList.getList());

        configReflect.oneList.getList().add(one);

        ExampleData.Two two = new ExampleData.Two();
        two.sec_number = 789;
        two.sec_text = "j'espere que ça marche";
        configReflect.customMap.getMap().put("Woow", two);



        /* */  configReflect.save();
        /* you can remove this code (only if file is generated) */




        configReflect.load();

        assertEquals("Heey", configReflect.text);
        assertEquals(666, configReflect.number);
        assertTrue(configReflect.map.containsKey("ok"));







    }

}
