/*
*
 */
package bc.bind.model;

import bc.bind.model.api.IDataSet;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author codetime
 */
public class DataSet {

    private JsonObjectBuilder jsonObj;

    private String index;
    private String previousIndex;
    private String[] recipients;

    public DataSet(String index,
            String previousIndex,
            String[] recipients) {

        jsonObj = null;

        this.index = index;
        this.previousIndex = previousIndex;
        this.recipients = recipients;
    }

    public JsonObjectBuilder create(IDataSet dataSet) throws Exception {

        jsonObj = Json.createObjectBuilder()
                .add("index", index)
                .add("previousIndex", previousIndex);
        jsonObj.add("recipients", recipients(recipients));
        jsonObj.add("body", dataSet.create());

        return jsonObj;
    }

    public JsonArrayBuilder recipients(String[] ids) throws Exception {
        JsonArrayBuilder jarr = Json.createArrayBuilder();
        for (int i = 0; i < ids.length; i++) {
            jarr.add(ids[i]);
        }
        return jarr;
    }

    public JsonObject build() throws Exception {
        return jsonObj.build();
    }
}
