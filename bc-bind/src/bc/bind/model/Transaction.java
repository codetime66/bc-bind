/*
*
 */
package bc.bind.model;

import bc.bind.model.api.IBlockBody;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author codetime
 */
public class Transaction {

    private JsonObjectBuilder jsonObj;

    private String index;
    private String previousIndex;
    private String[] recipients;
    
    public Transaction() {
        jsonObj = null;
        index=null;
        previousIndex=null;
        recipients=null;
    }
    
    public JsonObjectBuilder create(IBlockBody blockBody) throws Exception {

        jsonObj = Json.createObjectBuilder()
                .add("index", index)
                .add("previousIndex", previousIndex);
        jsonObj.add("recipients", recipients(recipients));
        jsonObj.add("body", blockBody.create());

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
