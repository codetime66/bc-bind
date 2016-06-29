/*
*
 */
package bc.bind.model;

import bc.bind.model.api.IDataSet;
import bc.cipher.api.Cipher;
import bc.cipher.api.CipherFactory;
import javax.json.Json;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author codetime
 */
public class Entry implements IDataSet {

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
            String recipient) throws Exception {
        jsonObj = null;
        this.sender = sender;
        this.hash = hash;
        this.sign = sign;
        this.dataSet = dataSet;
        this.recipient = recipient;
        index = getIndex();
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

    private String getIndex() throws Exception {
        Cipher cipher = (Cipher) CipherFactory.getInstance("MDGenerator");
        StringBuilder sb = new StringBuilder();
        sb.append(sender);
        sb.append(hash);
        sb.append(sign);
        sb.append(dataSet);
        sb.append(recipient);

        return cipher.perform( new String[]{sb.toString()} );
    }
}
