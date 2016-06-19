/*
*
 */
package bc.bind.model;

import bc.bind.model.api.IDataSet;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author codetime
 */
public class Operation implements IDataSet {

    private JsonObjectBuilder jsonObj;

    private String operationType;
    private String p1;
    private String p2;
    private String financialInstrument;
    private String settlementMode;
    private String quantity;
    private String price;
    private String financialValue;
    private String date;

    public Operation(String operationType,
            String p1,
            String p2,
            String financialInstrument,
            String settlementMode,
            String quantity,
            String price,
            String financialValue,
            String date) {

        jsonObj = null;

        this.operationType = operationType;
        this.p1 = p1;
        this.p2 = p2;
        this.financialInstrument = financialInstrument;
        this.settlementMode = settlementMode;
        this.quantity = quantity;
        this.price = price;
        this.financialValue = financialValue;
        this.date = date;
    }

    public JsonObjectBuilder create() throws Exception {
        jsonObj = Json.createObjectBuilder()
                .add("operationType", operationType)
                .add("p1", p1)
                .add("p2", p2)
                .add("financialInstrument", financialInstrument)
                .add("settlementMode", settlementMode)
                .add("quantity", quantity)
                .add("price", price)
                .add("financialValue", financialValue)
                .add("date", date);
        return jsonObj;
    }

    public JsonObject build() throws Exception {
        return jsonObj.build();
    }
}
