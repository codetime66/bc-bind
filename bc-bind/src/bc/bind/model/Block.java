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
public class Block {

    private JsonObjectBuilder jsonObj;

    private String index;
    private String previousIndex;
    private String[] recipients;
    
    public Block() {
        jsonObj = null;
        index=null;
        previousIndex=null;
        recipients=null;
    }

    public void setIndex(String index){
       this.index=index;        
    }
    public void setPreviousIndex(String previousIndex){
       this.previousIndex=previousIndex;
    }
    public void setRecipients(String[] recipients){
       this.recipients=recipients;
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
