/*
*
 */
package bc.bind;

import bc.bind.model.Derivative;
import bc.bind.model.Entry;
import bc.bind.model.FinancialInstrument;
import bc.bind.model.Operation;

/**
 *
 * @author codetime
 */
public class EntryBind {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {

            //creating an asset DataSet
            FinancialInstrument financialInstrument = new FinancialInstrument("SWAP",
                    "10020.00-1",
                    "16D00000001",
                    "1000",
                    "10",
                    "10000",
                    "2016-06-17");

            Derivative derivative = new Derivative(financialInstrument,
                    "10020.00-1",
                    "DI",
                    "100",
                    "1000",
                    "05000.00-5",
                    "USD",
                    "100",
                    "1000");

            //json model toStream
            System.out.println("derivative.toStream: " + derivative.toStream(derivative.build(derivative.create())));
            
            //building a entry based on a asset DataSet
            System.out.println("Entry:");
            Entry entryAsset = new Entry("10020.00-1-PubKey",
            "datasethash",
            "datasetsign",
            derivative,
            "05000.00-5-PubKey");
            System.out.println("entryAsset.toStream: " + entryAsset.toStream(entryAsset.build(entryAsset.create())));            
            //---------------------------------------------
            
            //creating an operation DataSet
            Operation operation = new Operation("0001",
                    "10020.00-1",
                    "05000.00-5",
                    "16D00000001",
                    "SM",
                    "10",
                    "1000",
                    "10000",
                    "2016-06-17");

            //json model toStream
            System.out.println("operation.toStream: " + operation.toStream(operation.build(operation.create())));            
            
            //building a entry based on an operation DataSet
            System.out.println("Entry:");
            Entry entryOperation = new Entry("10020.00-1-PubKey",
            "datasethash",
            "datasetsign",
            operation,
            "05000.00-5-PubKey");
            System.out.println("entryOperation.toStream: " + entryOperation.toStream(entryOperation.build(entryOperation.create())));            
            //---------------------------------------------
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
