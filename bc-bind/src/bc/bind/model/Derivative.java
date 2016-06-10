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
public class Derivative implements IBlockBody {
    
    private JsonObjectBuilder jsonObj;
    
    private String p1;
    private String financialIndexP1;
    private String percentageP1;
    private String financialValueP1;
    private String p2;    
    private String financialIndexP2;
    private String percentageP2;
    private String financialValueP2;
    
    public Derivative() {
        jsonObj = null;    
        p1=null;
        financialIndexP1=null;
        percentageP1=null;
        financialValueP1=null;
        p2=null;    
        financialIndexP2=null;
        percentageP2=null;
        financialValueP2=null;
    }
    
    public void setP1(String p1){
        this.p1=p1;
    }
    
    public void setFinancialIndexP1( String financialIndexP1){
        this.financialIndexP1=financialIndexP1;
    }
    
    public void setPercentageP1(String percentageP1){
        this.percentageP1=percentageP1;
    }
    
    public void setFinancialValueP1(String financialValueP1){
        this.financialValueP1=financialValueP1;
    }
    
    public void setP2(String p2){
        this.p2=p2;
    }   
    
    public void setFinancialIndexP2(String financialIndexP2){
        this.financialIndexP2=financialIndexP2;
    }
    
    public void setPercentageP2(String percentageP2){
        this.percentageP2=percentageP2;
    }
    
    public void setFinancialValuP2(String financialValueP2){
        this.financialValueP2=financialValueP2;
    }
    
    public JsonObjectBuilder create() throws Exception {
        FinancialInstrument fi = new FinancialInstrument();
        jsonObj = fi.create();
                
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
