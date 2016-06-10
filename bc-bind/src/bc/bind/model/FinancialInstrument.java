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
public class FinancialInstrument implements IBlockBody {
    
    private JsonObjectBuilder jsonObj;
    
    private String type;
    private String issuing;
    private String code;
    private String quantity;
    private String price;
    private String financialValue;
    private String date;
    
    public FinancialInstrument() {
        jsonObj = null;
        type=null;
        issuing=null;
        code=null;
        quantity=null;
        price=null;
        financialValue=null;
        date=null;
    }

    public void setType(String type){
        this.type=type;
    }
    public void setIssuing(String issuing){
        this.issuing=issuing;
    }
    public void setCode(String code){
        this.code=code;
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
                .add("assetType", type)
                .add("issuing", issuing)
                .add("code",  code)
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
