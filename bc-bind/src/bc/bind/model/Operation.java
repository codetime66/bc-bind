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
public class Operation implements IBlockBody {

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
    
    public Operation() {
       jsonObj = null;
       operationType=null;
       p1=null;
       p2=null;
       financialInstrument=null;
       settlementMode=null;
       quantity=null;
       price=null;
       financialValue=null;
       date=null;
    }

    public void setOperationType(String operationType){
       this.operationType=operationType;
    }
    public void setP1(String p1){
       this.p1=p1;
    }
    public void setP2(String p2){
       this.p2=p2;
    }
    public void setFinancialInstrument(String financialInstrument){
       this.financialInstrument=financialInstrument;
    }
    public void setSettlementMode(String settlementMode){
       this.settlementMode=settlementMode; 
    }
    public void setQuantity(String quantity){
       this.quantity=quantity;
    }
    public void setPrice(String price){
       this.price=price;
    }
    public void setFinancialValue(String financialValue){
       this.financialValue=financialValue;
    }
    public void setDate(String date){
       this.date=date; 
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
