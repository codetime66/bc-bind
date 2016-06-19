/*
 *
 */
package bc.bind.model.api;

import java.io.StringWriter;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

/**
 *
 * @author codetime
 */
public interface IDataSet {
    public JsonObjectBuilder create() throws Exception;
    
    public default JsonObject build(JsonObjectBuilder jsonObj) throws Exception {
        return jsonObj.build();
    }    
    
    public default String toStream(JsonObject model) throws Exception {
        StringWriter stWriter = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(stWriter);
        jsonWriter.writeObject(model);
        jsonWriter.close();
        return stWriter.toString();
    }
}
