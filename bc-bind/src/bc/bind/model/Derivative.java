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
public class Derivative implements IDataSet {

    private JsonObjectBuilder jsonObj;

    private FinancialInstrument financialInstrument;
    private String p1;
    private String financialIndexP1;
    private String percentageP1;
    private String financialValueP1;
    private String p2;
    private String financialIndexP2;
    private String percentageP2;
    private String financialValueP2;

    public Derivative(FinancialInstrument financialInstrument,
            String p1,
            String financialIndexP1,
            String percentageP1,
            String financialValueP1,
            String p2,
            String financialIndexP2,
            String percentageP2,
            String financialValueP2) {

        jsonObj = null;

        this.financialInstrument = financialInstrument;
        this.p1 = p1;
        this.financialIndexP1 = financialIndexP1;
        this.percentageP1 = percentageP1;
        this.financialValueP1 = financialValueP1;
        this.p2 = p2;
        this.financialIndexP2 = financialIndexP2;
        this.percentageP2 = percentageP2;
        this.financialValueP2 = financialValueP2;
    }

    public JsonObjectBuilder create() throws Exception {

        jsonObj = financialInstrument.create();

        JsonObjectBuilder derivative = Json.createObjectBuilder()
                .add("p1", p1)
                .add("financialIndexP1", financialIndexP1)
                .add("percentageP1", percentageP1)
                .add("financialValueP1", financialValueP1)
                .add("p2", p2)
                .add("financialIndexP2", financialIndexP2)
                .add("percentageP2", percentageP2)
                .add("financialValueP2", financialValueP2);

        jsonObj.add("detail", derivative);

        return jsonObj;
    }

    public JsonObject build() throws Exception {
        return jsonObj.build();
    }
}
