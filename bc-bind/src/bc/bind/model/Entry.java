/*
*
 */
package bc.bind.model;

import bc.bind.model.api.IDataSet;
import javax.json.Json;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author codetime
 */
public class Entry implements IDataSet{

    private JsonObjectBuilder jsonObj;

    private String index;
    private String sender;
    private String hash;
    private String sign;
    private IDataSet dataSet;
    private String recipient;

    public Entry(String sender,
            String hash,
            String sign,
            IDataSet dataSet,
            String recipient) {
        jsonObj = null;
        this.sender = sender;
        this.hash = hash;
        this.sign = sign;
        this.dataSet = dataSet;
        this.recipient = recipient;

        //TODO: change to digest algorithm
        index = "ENTRYINDEX"+hashCode();
    }

    public JsonObjectBuilder create() throws Exception {

        jsonObj = Json.createObjectBuilder()
                .add("index", index)
                .add("sender", sender)
                .add("hash", hash)
                .add("sign", sign)
                .add("dataSet", dataSet.create())
                .add("recipient", recipient);

        return jsonObj;
    }
    
    @Override
    public int hashCode() {
        int hashcode = 1;
        hashcode = hashcode * 17 + (sender == null ? 0 : sender.hashCode());
        hashcode = hashcode * 31 + (hash == null ? 0 : hash.hashCode());
        hashcode = hashcode * 13 + (sign == null ? 0 : sign.hashCode());
        hashcode = hashcode * 13 + (dataSet == null ? 0 : dataSet.hashCode());
        hashcode = hashcode * 13 + (recipient == null ? 0 : recipient.hashCode());
        return hashcode;
    }    
}
