/*
*
 */
package bc.bind.model;

import bc.bind.model.api.IBlockBody;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author codetime
 */
public class Entry {

    private JsonObjectBuilder jsonObj;

    private String index;
    private String sender;
    private String hash;
    private String sign;
    private String encrypted;
    private String recipient;

    public Entry(String sender,
            String hash,
            String sign,
            String encrypted,
            String recipient) {
        jsonObj = null;
        this.sender = sender;
        this.hash = hash;
        this.sign = sign;
        this.encrypted = encrypted;
        this.recipient = recipient;

        //TODO: get hashcode for sender, recipient and hash to build the entry index
        index = null;
    }

    public JsonObjectBuilder create(IBlockBody blockBody) throws Exception {

        jsonObj = Json.createObjectBuilder()
                .add("index", index)
                .add("sender", sender)
                .add("hash", hash)
                .add("sign", sign)
                .add("encrypted", encrypted)
                .add("recipient", recipient);

        return jsonObj;
    }

    public JsonObject build() throws Exception {
        return jsonObj.build();
    }
}
