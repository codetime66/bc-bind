/*
*
*/
package bc.bind;

import java.io.StringWriter;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

/**
 *
 * @author codetime
 */
public class BuildData {

    public BuildData() {

    }

    public void toStream(JsonObject model) throws Exception {
        StringWriter stWriter = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(stWriter);
        jsonWriter.writeObject(model);
        jsonWriter.close();

        String jsonData = stWriter.toString();
        System.out.println(jsonData);

    }
}
