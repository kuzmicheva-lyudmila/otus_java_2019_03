import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.Test;
import ru.cft.hw_json.classExamples.FirstSerializeClass;
import ru.cft.hw_json.Parser;
import ru.cft.hw_json.classExamples.SecondSerializeClass;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestJsonParser {

    @Test
    public void jsonParserTest() {
        Gson gson = new Gson();

        List<String> listOfString = new ArrayList<>();
        listOfString.add("Hello");
        listOfString.add("world!!!");

        SecondSerializeClass secondSerializeClass = new SecondSerializeClass(2, "world", 'h');
        FirstSerializeClass firstSerializeClass = new FirstSerializeClass(false, new SecondSerializeClass[]{new SecondSerializeClass(2, "world", 'a'), new SecondSerializeClass(4, "hello", 'b')}, listOfString);
        String resultJson = new Parser().parseToJson(firstSerializeClass);

        FirstSerializeClass object;
        try {
            object = gson.fromJson(resultJson, FirstSerializeClass.class);
        } catch (JsonSyntaxException e) {
            object = null;
        }
        assertNotEquals(object, null);
    }
}
