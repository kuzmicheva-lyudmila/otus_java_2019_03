import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import ru.cft.hw_json.classExamples.FirstSerializeClass;
import ru.cft.hw_json.Parser;
import ru.cft.hw_json.classExamples.SecondSerializeClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonParser {

    @Test
    public void jsonParserTestFirst() {
        Gson gson = new Gson();
        Parser serializer = new Parser();

        List<String> listOfString = new ArrayList<>();
        listOfString.add("Hello");
        listOfString.add("world!!!");

        FirstSerializeClass firstSerializeClass = new FirstSerializeClass(false, new SecondSerializeClass[]{new SecondSerializeClass(null, "world", 'a'), new SecondSerializeClass(4, "hello", 'b')}, listOfString, null);

        assertEquals(gson.toJson(firstSerializeClass), serializer.parseToJson(firstSerializeClass));
    }

    @Test
    public void jsonParserTestSecond() {
        Gson gson = new Gson();
        Parser serializer = new Parser();

        assertEquals(gson.toJson(null), serializer.parseToJson(null));
        assertEquals(gson.toJson((byte) 1), serializer.parseToJson((byte) 1));
        assertEquals(gson.toJson((short) 1f), serializer.parseToJson((short) 1f));
        assertEquals(gson.toJson(1), serializer.parseToJson(1));
        assertEquals(gson.toJson(1L), serializer.parseToJson(1L));
        assertEquals(gson.toJson(1f), serializer.parseToJson(1f));
        assertEquals(gson.toJson(1d), serializer.parseToJson(1d));
        assertEquals(gson.toJson("aaa"), serializer.parseToJson("aaa"));
        assertEquals(gson.toJson('a'), serializer.parseToJson('a'));
        assertEquals(gson.toJson(new int[]{1, 2, 3}), serializer.parseToJson(new int[]{1, 2, 3}));

        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, 1, 2, 3);
        assertEquals(gson.toJson(list), serializer.parseToJson(list));
        assertEquals(gson.toJson(Collections.singletonList(1)), serializer.parseToJson(Collections.singletonList(1)));
    }
}
