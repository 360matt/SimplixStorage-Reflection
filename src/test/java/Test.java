import static org.junit.Assert.*;
import java.util.Arrays;

public class Test {

    @org.junit.Test
    public static void main (String[] args) {

        ExampleData configReflect = new ExampleData();


        /* you can remove this code (only if file is generated) */
        /* */  configReflect.load();
        /* */  configReflect.text = "Heey";
        /* */  configReflect.number = 666;
        /* */  configReflect.map.put("ok", "kayak");
        /* */  configReflect.list = Arrays.asList(20, 50);
        /* */  configReflect.save();
        /* you can remove this code (only if file is generated) */




        configReflect.load();

        assertEquals("Heey", configReflect.text);
        assertEquals(666, configReflect.number);
        assertTrue(configReflect.map.containsKey("ok"));







    }

}
