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
public class FinancialInstrument implements IDataSet {

    private JsonObjectBuilder jsonObj;

    private String type;
    private String issuing;
    private String code;
    private String quantity;
    private String price;
    private String financialValue;
    private String date;

    public FinancialInstrument(String type,
            String issuing,
            String code,
            String quantity,
            String price,
            String financialValue,
            String date) {

        jsonObj = null;

        this.type = type;
        this.issuing = issuing;
        this.code = code;
        this.quantity = quantity;
        this.price = price;
        this.financialValue = financialValue;
        this.date = date;
    }

    public JsonObjectBuilder create() throws Exception {
        jsonObj = Json.createObjectBuilder()
                .add("assetType", type)
                .add("issuing", issuing)
                .add("code", code)
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
